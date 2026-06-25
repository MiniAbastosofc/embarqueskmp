"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.IngresosRepository = void 0;
const mssql_1 = __importDefault(require("mssql"));
const database_1 = require("../../../../config/database");
class IngresosRepository {
    async create(crear) {
        let pool;
        try {
            pool = await mssql_1.default.connect(database_1.dbConfig);
            const result = await pool
                .request()
                .input("origen_dinero_id", mssql_1.default.Int, crear.origenDineroId)
                .input("reservacion_id", mssql_1.default.Int, crear.reservacionId)
                .input("concepto_pago_id", mssql_1.default.Int, crear.conceptoPagoId)
                .input("monto_bruto", mssql_1.default.Decimal(10, 2), crear.montoBruto)
                .input("aplica_comision", mssql_1.default.Bit, crear.aplicaComision ? 1 : 0)
                .input("monto_comision", mssql_1.default.Decimal(10, 2), crear.montoComision)
                .input("user_create", mssql_1.default.Int, crear.userCreate)
                .execute("dbo.AAC_Alcatraz_AgregarPagoYFinalizarReserva");
            //   query(`
            //   INSERT INTO AAC_Alcatraz_pagos_cliente (
            //     origen_dinero_id,
            //     reservacion_id,
            //     concepto_pago_id,
            //     monto_bruto,
            //     aplica_comision,
            //     monto_comision,
            //     user_create,
            //     created_at
            //   ) VALUES (
            //     @origen_dinero_id,
            //     @reservacion_id,
            //     @concepto_pago_id,
            //     @monto_bruto,
            //     @aplica_comision,
            //     @monto_comision,
            //     @user_create,
            //     GETDATE()
            //   )
            // `);
        }
        catch (error) {
            throw new Error(`Error al registrar el pago en la BD: ${error.message}`);
        }
    }
    async obtenerPagosPorReservaId(id) {
        let pool;
        try {
            pool = await mssql_1.default.connect(database_1.dbConfig);
            const result = await pool.request().input("id", mssql_1.default.Int, id).query(`
        SELECT
          res.id AS FolioReserva,
          cli.nombre, 
          cli.apellido, 
          cli.telefono,
          cli.email,
          res.fecha_inicio, 
          res.fecha_fin,
          est.descripcion AS EstadoReservacion,
          con.id AS conceptoDePagoId,
          con.descripcion AS ConceptoPago,
          res.monto_total, 
          pagos.monto_bruto, 
          pagos.aplica_comision, 
          pagos.monto_comision,
          pagos.monto_neto 
        FROM AAC_Alcatraz_reservaciones res
          JOIN AAC_Alcatraz_clientes cli ON cli.id = res.cliente_id
          JOIN AAC_Alcatraz_estado_reservacion est ON est.id = res.estado_reservacion_id
          LEFT JOIN AAC_Alcatraz_pagos_cliente pagos ON pagos.reservacion_id = res.id
          LEFT JOIN AAC_Alcatraz_concepto_pago con ON con.id = pagos.concepto_pago_id
        WHERE res.id = @id
        ORDER BY pagos.id DESC
      `);
            return result.recordset.map((row) => ({
                folioReserva: Number(row.FolioReserva),
                nombre: row.nombre,
                apellido: row.apellido,
                telefono: row.telefono,
                email: row.email,
                fechaInicio: row.fecha_inicio,
                fechaFin: row.fecha_fin,
                estadoReservacion: row.EstadoReservacion,
                conceptoDePagoId: Number(row.conceptoDePagoId),
                conceptoPago: row.ConceptoPago ? row.ConceptoPago.trim() : "",
                montoTotalReserva: Number(row.monto_total),
                montoBrutoPago: Number(row.monto_bruto),
                aplicaComisionPago: row.aplica_comision === 1 || row.aplica_comision === true,
                montoComisionPago: Number(row.monto_comision),
                montoNetoPago: Number(row.monto_neto),
            }));
        }
        catch (error) {
            throw new Error(`Error al obtener el historial de pagos: ${error.message}`);
        }
    }
}
exports.IngresosRepository = IngresosRepository;
