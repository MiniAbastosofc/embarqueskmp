"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.FechasOcupadasRepository = void 0;
const mssql_1 = __importDefault(require("mssql"));
const database_1 = require("../../../../config/database");
class FechasOcupadasRepository {
    async obtenerFechasOcupadas() {
        let pool;
        try {
            pool = await mssql_1.default.connect(database_1.dbConfig);
            const result = await pool.request().query(`
        SELECT 
          r.fecha_inicio AS fechaDesde,
          r.fecha_fin AS fechaHasta,
          (ISNULL(c.nombre, '') + ' ' + ISNULL(c.apellido, '')) AS nombreCliente,
          o.nombre AS plataformaOrigen
        FROM 
          AAC_Alcatraz_reservaciones r
        INNER JOIN 
          AAC_Alcatraz_clientes c ON r.cliente_id = c.id
        INNER JOIN 
          AAC_Alcatraz_origen_reserva o ON r.origen_reserva_id = o.id
        ORDER BY 
          r.fecha_inicio ASC;
      `);
            return result.recordset.map((row) => ({
                fechaDesde: row.fechaDesde,
                fechaHasta: row.fechaHasta,
                nombreCliente: row.nombreCliente,
                plataformaOrigen: row.plataformaOrigen,
            }));
        }
        catch (error) {
            throw new Error(`Error al obtener fechas ocupadas: ${error.message}`);
        }
    }
}
exports.FechasOcupadasRepository = FechasOcupadasRepository;
