import mssql from "mssql";
import { dbConfig } from "../../config/database";

// Importamos el script desde la infraestructura del módulo auth
import { createUsuariosTable } from "../../features/auth/infrastructure/database/create-table-ususarios";
import { createReservasTable } from "../../features/reservas/infrastructure/database/create-table-reservas";

// En este arreglo irás agregando las tablas de los demás módulos (income, booking, etc.)
const scriptsDeInicializacion: string[] = [
  createUsuariosTable,
  createReservasTable,
];

export const initializeDatabase = async (): Promise<mssql.ConnectionPool> => {
  try {
    const pool = await mssql.connect(dbConfig);
    console.log("📦 Base de datos conectada con éxito.");

    for (const script of scriptsDeInicializacion) {
      await pool.request().query(script);
    }

    console.log(
      "🚀 Todas las tablas de los features han sido verificadas/creadas.",
    );
    return pool;
  } catch (error) {
    console.error("❌ Error crítico en la infraestructura de BD:", error);
    process.exit(1);
  }
};
