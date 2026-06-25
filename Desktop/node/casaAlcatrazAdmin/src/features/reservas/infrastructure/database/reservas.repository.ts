import mssql from "mssql";
import {
  CrearReservaInput,
  IReservaRepository,
  Reserva,
} from "../../domain/reservas.respository";
import { dbConfig } from "../../../../config/database";

export class ReservasRepository implements IReservaRepository {
  async findAll(): Promise<Reserva[]> {
    let pool;
    try {
      pool = await mssql.connect(dbConfig);

      // Ajusta el SELECT con los nombres exactos de tus columnas en la BD
      const result = await pool.request().query(`
      SELECT 
        res.id AS FolioReserva, 
        cli.nombre, 
        cli.apellido, 
        cli.telefono, 
        cli.email,
        res.fecha_inicio, 
        res.fecha_fin, 
        est.descripcion AS estadoDescripcion, 
        ori.nombre AS origenNombre, 
        res.monto_total, 
        res.total_dias_reservados, 
        res.personas_hospedadas,
        
        ISNULL((SELECT SUM(p.monto_bruto) FROM AAC_Alcatraz_pagos_cliente p WHERE p.reservacion_id = res.id), 0) AS montoPagado,
        (res.monto_total - ISNULL((SELECT SUM(p.monto_bruto) FROM AAC_Alcatraz_pagos_cliente p WHERE p.reservacion_id = res.id), 0)) AS saldoPendiente,
        CASE 
          WHEN ISNULL((SELECT SUM(p.monto_bruto) FROM AAC_Alcatraz_pagos_cliente p WHERE p.reservacion_id = res.id), 0) >= res.monto_total THEN 'Pagado'
          ELSE 'Pendiente'
        END AS estatusPago
      FROM AAC_Alcatraz_reservaciones res
      JOIN AAC_Alcatraz_clientes cli ON res.cliente_id = cli.id
      JOIN AAC_Alcatraz_estado_reservacion est ON res.estado_reservacion_id = est.id
      JOIN AAC_Alcatraz_origen_reserva ori ON res.origen_reserva_id = ori.id
      ORDER BY FolioReserva DESC
    `);

      // Mapeamos lo que viene de la BD al objeto "Reserva" del Dominio
      return result.recordset.map((row) => ({
        id: row.FolioReserva,
        nombreCliente: row.nombre,
        apellidoCliente: row.apellido,
        telefono: row.telefono,
        email: row.email,
        fechaInicio: row.fecha_inicio,
        fechaFin: row.fecha_fin,
        estadoDescripcion: row.estadoDescripcion,
        origenNombre: row.origenNombre,
        montoTotal: Number(row.monto_total),
        totalDiasReservacion: row.total_dias_reservados,
        personasHospedadas: row.personas_hospedadas,

        montoPagado: Number(row.montoPagado),
        saldoPendiente: Number(row.saldoPendiente),
        estatusPago: row.estatusPago,
      }));
    } catch (error) {
      throw new Error(`Error al obtener reservas: ${(error as Error).message}`);
    }
  }

  // 2. BUSCAR POR ID
  async findById(id: number): Promise<Reserva | null> {
    let pool;
    try {
      pool = await mssql.connect(dbConfig);

      // Tu query exacto filtrando por ID usando parámetros seguros
      const result = await pool.request().input("id", mssql.Int, id).query(`
        SELECT 
          res.id AS FolioReserva, 
          cli.nombre, 
          cli.apellido, 
          cli.telefono, 
          cli.email,
          res.fecha_inicio, 
          res.fecha_fin, 
          est.descripcion AS estadoDescripcion, 
          ori.nombre AS origenNombre, 
          res.monto_total, 
          res.total_dias_reservados, 
          res.personas_hospedadas,
          
          ISNULL((SELECT SUM(p.monto_bruto) FROM AAC_Alcatraz_pagos_cliente p WHERE p.reservacion_id = res.id), 0) AS montoPagado,
          (res.monto_total - ISNULL((SELECT SUM(p.monto_bruto) FROM AAC_Alcatraz_pagos_cliente p WHERE p.reservacion_id = res.id), 0)) AS saldoPendiente,
          CASE 
            WHEN ISNULL((SELECT SUM(p.monto_bruto) FROM AAC_Alcatraz_pagos_cliente p WHERE p.reservacion_id = res.id), 0) >= res.monto_total THEN 'Pagado'
            ELSE 'Pendiente'
          END AS estatusPago,
          DATEDIFF(day, GETDATE(), res.fecha_inicio) AS diasParaCheckIn
        FROM AAC_Alcatraz_reservaciones res
        JOIN AAC_Alcatraz_clientes cli ON res.cliente_id = cli.id
        JOIN AAC_Alcatraz_estado_reservacion est ON res.estado_reservacion_id = est.id
        JOIN AAC_Alcatraz_origen_reserva ori ON res.origen_reserva_id = ori.id
        WHERE res.id = @id
      `);

      const row = result.recordset[0];
      if (!row) return null;

      return {
        id: row.FolioReserva,
        nombreCliente: row.nombre,
        apellidoCliente: row.apellido,
        telefono: row.telefono,
        email: row.email,
        fechaInicio: row.fecha_inicio,
        fechaFin: row.fecha_fin,
        estadoDescripcion: row.estadoDescripcion,
        origenNombre: row.origenNombre,
        montoTotal: Number(row.monto_total),
        totalDiasReservacion: row.total_dias_reservados,
        personasHospedadas: row.personas_hospedadas,

        montoPagado: Number(row.montoPagado),
        saldoPendiente: Number(row.saldoPendiente),
        estatusPago: row.estatusPago,
        diasParaCheckIn: Number(row.diasParaCheckIn),
      };
    } catch (error) {
      throw new Error(
        `Error al buscar reserva por ID: ${(error as Error).message}`,
      );
    }
  }

  // 3. CREAR RESERVA (Ejecutando tu Procedimiento Almacenado)
  async create(
    reserva: CrearReservaInput,
  ): Promise<{ id: number } & CrearReservaInput> {
    let pool;
    try {
      pool = await mssql.connect(dbConfig);

      let extrasXml: string | null = null;

      if (reserva.extras && reserva.extras.length > 0) {
        // Mapeamos cada objeto del array a etiquetas <item> estándar
        const itemsXml = reserva.extras
          .map(
            (ext) => `
          <item>
            <concepto>${ext.concepto.replace(/</g, "&lt;").replace(/>/g, "&gt;")}</concepto>
            <monto>${ext.monto}</monto>
          </item>`,
          )
          .join("");

        // Envolvemos todo en la etiqueta raíz <extras> que espera el SP
        extrasXml = `<extras>${itemsXml}</extras>`;
      }

      const result = await pool
        .request()
        // Tu SP ahora recibe exactamente lo que espera sin que TypeScript se queje
        .input("IdUsuario", mssql.Int, reserva.usuarioId)
        .input("NombreCliente", mssql.VarChar, reserva.nombreCliente)
        .input("ApellidoCliente", mssql.VarChar, reserva.apellidoCliente)
        .input("Telefono", mssql.VarChar, reserva.telefono)
        .input("Email", mssql.VarChar, reserva.email)
        .input("FechaInicio", mssql.DateTime, reserva.fechaInicio)
        .input("FechaFin", mssql.DateTime, reserva.fechaFin)
        .input("TotalDiasReservados", mssql.Int, reserva.totalDiasReservacion)
        .input("MontoTotal", mssql.Decimal(10, 2), reserva.montoTotal)
        .input("OrigenReservaId", mssql.Int, reserva.origenReservaId)
        .input(
          "NuevoOrigenNombre",
          mssql.VarChar,
          reserva.nuevoOrigenNombre || null,
        )
        .input("PersonasHospedadas", mssql.Int, reserva.personasHospedadas)
        .input("ExtrasXml", mssql.Xml, extrasXml)
        .execute("AAC_Alcatraz_Crear_Reservacion");

      // Capturamos el ID generado por tu SP
      const idCreado = result.recordset[0]?.id || 999;

      // Retornamos el ID junto con los datos que se usaron para crearla
      return {
        id: idCreado,
        ...reserva,
      };
    } catch (error) {
      throw new Error(
        `Error al ejecutar el SP de creación: ${(error as Error).message}`,
      );
    }
  }
}
