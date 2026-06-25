"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.initializeDatabase = void 0;
const mssql_1 = __importDefault(require("mssql"));
const database_1 = require("../../config/database");
// Importamos el script desde la infraestructura del módulo auth
const create_table_ususarios_1 = require("../../features/auth/infrastructure/database/create-table-ususarios");
const create_table_reservas_1 = require("../../features/reservas/infrastructure/database/create-table-reservas");
// En este arreglo irás agregando las tablas de los demás módulos (income, booking, etc.)
const scriptsDeInicializacion = [
    create_table_ususarios_1.createUsuariosTable,
    create_table_reservas_1.createReservasTable,
];
const initializeDatabase = async () => {
    try {
        const pool = await mssql_1.default.connect(database_1.dbConfig);
        console.log("📦 Base de datos conectada con éxito.");
        for (const script of scriptsDeInicializacion) {
            await pool.request().query(script);
        }
        console.log("🚀 Todas las tablas de los features han sido verificadas/creadas.");
        return pool;
    }
    catch (error) {
        console.error("❌ Error crítico en la infraestructura de BD:", error);
        process.exit(1);
    }
};
exports.initializeDatabase = initializeDatabase;
