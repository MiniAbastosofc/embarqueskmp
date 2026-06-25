"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.ResumenRepository = void 0;
const mssql_1 = __importDefault(require("mssql"));
const database_1 = require("../../../../config/database");
class ResumenRepository {
    async obtenerResumenDashboard() {
        let pool;
        try {
            pool = await mssql_1.default.connect(database_1.dbConfig);
            // Creamos la solicitud para la base de datos
            const request = pool.request();
            // Ejecutamos ambos queries en paralelo para máxima velocidad
            const [resultReservaciones, resultFinanzas] = await Promise.all([
                request.query(`
          SELECT 
            COUNT(*) AS TotalDeReservaciones, 
            SUM(monto_total) AS montoReservaciones 
          FROM AAC_Alcatraz_reservaciones
        `),
                request.query(`
          SELECT 
            SUM(monto_bruto) AS MontosBrutos, 
            SUM(monto_comision) AS Comisiones, 
            SUM(monto_neto) AS Total 
          FROM AAC_Alcatraz_pagos_cliente
        `),
            ]);
            // Extraemos la primera fila de cada resultado
            const filaReservaciones = resultReservaciones.recordset[0];
            const filaFinanzas = resultFinanzas.recordset[0];
            // Mapeamos los datos respetando los alias de tus queries hacia tu interfaz del Dominio
            return {
                reservaciones: {
                    // Usamos Number() y || 0 por si la tabla está vacía y SQL devuelve NULL
                    totalReservaciones: Number(filaReservaciones?.TotalDeReservaciones || 0),
                    montoReservaciones: Number(filaReservaciones?.montoReservaciones || 0),
                },
                finanzas: {
                    montosBrutos: Number(filaFinanzas?.MontosBrutos || 0),
                    comisiones: Number(filaFinanzas?.Comisiones || 0),
                    totalNeto: Number(filaFinanzas?.Total || 0),
                },
            };
        }
        catch (error) {
            // Lanzamos el error hacia arriba para que el controlador lo atrape en su catch
            throw new Error(`Error en la base de datos al generar el resumen: ${error.message}`);
        }
    }
    async obtenerDashboardCompleto() {
        let pool;
        try {
            pool = await mssql_1.default.connect(database_1.dbConfig);
            // Ejecutamos los 5 queries en paralelo con requests independientes para máxima velocidad
            const [resFinanzas, resPlataformas, resHistorico, resTopClientes, resEstados,] = await Promise.all([
                pool.request().query(`
          SELECT 
            SUM(monto_bruto) AS [Total Bruto],
            SUM(monto_comision) AS [Total Comisiones],
            SUM(monto_neto) AS [Total Neto Ganado]
          FROM AAC_Alcatraz_pagos_cliente;
        `),
                pool.request().query(`
          SELECT 
            o.nombre AS [Plataforma],
            COUNT(r.id) AS [Cantidad Reservaciones],
            SUM(r.monto_total) AS [Total Dinero Generado]
          FROM AAC_Alcatraz_reservaciones r
          INNER JOIN AAC_Alcatraz_origen_reserva o ON r.origen_reserva_id = o.id
          GROUP BY o.nombre
          ORDER BY [Total Dinero Generado] DESC;
        `),
                pool.request().query(`
          SELECT 
            COUNT(id) AS [Total Reservas Historicas],
            SUM(total_dias_reservados) AS [Total Dias Reservados],
            SUM(personas_hospedadas) AS [Total Huespedes Recibidos],
            AVG(total_dias_reservados) AS [Promedio de Dias por Reserva]
          FROM AAC_Alcatraz_reservaciones;
        `),
                pool.request().query(`
          SELECT TOP 10
            c.nombre + ' ' + c.apellido AS [Cliente],
            c.email AS [Correo],
            SUM(r.monto_total) AS [Total Dinero Gastado]
          FROM AAC_Alcatraz_clientes c
          INNER JOIN AAC_Alcatraz_reservaciones r ON c.id = r.cliente_id
          GROUP BY c.nombre, c.apellido, c.email
          ORDER BY [Total Dinero Gastado] DESC;
        `),
                pool.request().query(`
          SELECT 
            e.descripcion AS [Estado],
            COUNT(r.id) AS [Cantidad]
          FROM AAC_Alcatraz_reservaciones r
          INNER JOIN AAC_Alcatraz_estado_reservacion e ON r.estado_reservacion_id = e.id
          GROUP BY e.descripcion
          ORDER BY [Cantidad] DESC;
        `),
            ]);
            // Extraemos la primera fila de los resultados que solo devuelven un registro
            const filaFinanzas = resFinanzas.recordset[0];
            const filaHistorico = resHistorico.recordset[0];
            // Mapeamos los datos respetando tus interfaces del dominio
            return {
                finanzas: {
                    totalBruto: Number(filaFinanzas?.["Total Bruto"] || 0),
                    totalComisiones: Number(filaFinanzas?.["Total Comisiones"] || 0),
                    totalNetoGanado: Number(filaFinanzas?.["Total Neto Ganado"] || 0),
                },
                plataformas: resPlataformas.recordset.map((row) => ({
                    plataforma: String(row["Plataforma"] || ""),
                    cantidadReservaciones: Number(row["Cantidad Reservaciones"] || 0),
                    totalDineroGenerado: Number(row["Total Dinero Generado"] || 0),
                })),
                historico: {
                    totalReservasHistoricas: Number(filaHistorico?.["Total Reservas Historicas"] || 0),
                    totalDiasReservados: Number(filaHistorico?.["Total Dias Reservados"] || 0),
                    totalHuespedesRecibidos: Number(filaHistorico?.["Total Huespedes Recibidos"] || 0),
                    promedioDiasPorReserva: Number(filaHistorico?.["Promedio de Dias por Reserva"] || 0),
                },
                topClientes: resTopClientes.recordset.map((row) => ({
                    cliente: String(row["Cliente"] || ""),
                    correo: String(row["Correo"] || ""),
                    cantidadViajes: 0, // Como está comentado en tu SQL, lo inicializamos en 0 por ahora
                    totalDineroGastado: Number(row["Total Dinero Gastado"] || 0),
                })),
                estados: resEstados.recordset.map((row) => ({
                    estado: String(row["Estado"] || ""),
                    cantidad: Number(row["Cantidad"] || 0),
                })),
            };
        }
        catch (error) {
            throw new Error(`Error al procesar las métricas del dashboard: ${error.message}`);
        }
    }
}
exports.ResumenRepository = ResumenRepository;
