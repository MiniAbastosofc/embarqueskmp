"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.OrigenReservasRepository = void 0;
const mssql_1 = __importDefault(require("mssql"));
const database_1 = require("../../../../config/database"); // Ajusta la ruta a tu config de BD
class OrigenReservasRepository {
    async findAll() {
        let pool;
        try {
            // Abrimos la conexión tal como lo haces en reservas
            pool = await mssql_1.default.connect(database_1.dbConfig);
            // Consulta directa a la tabla maestra de tu diagrama
            const result = await pool.request().query(`
        SELECT 
          id, 
          nombre, 
          descripcion 
        FROM AAC_Alcatraz_origen_reserva
        ORDER BY nombre ASC
      `);
            // Mapeamos los registros de la BD al contrato del Dominio
            return result.recordset.map((row) => ({
                id: row.id,
                nombre: row.nombre,
                descripcion: row.descripcion || "",
            }));
        }
        catch (error) {
            // Error controlado de infraestructura
            throw new Error(`Error al obtener los orígenes de reserva: ${error.message}`);
        }
        finally {
            // Es una buena práctica cerrar o liberar el pool si tu arquitectura lo requiere
            // si en tu otro repositorio no lo cierras explícitamente, puedes omitir el finally
        }
    }
}
exports.OrigenReservasRepository = OrigenReservasRepository;
