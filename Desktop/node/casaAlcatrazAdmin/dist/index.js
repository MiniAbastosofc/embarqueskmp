"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
require("dotenv/config");
const db_init_1 = require("./shared/infrastructure/db-init");
const app_routes_1 = require("./shared/infrastructure/app.routes");
const cors = require("cors");
const app = (0, express_1.default)();
const PORT = process.env.PORT || 3000;
// Middlewares globales
app.use(express_1.default.json());
app.use(cors({
    origin: "*", //'http://172.16.3.213:5173',
    //credentials: true
}));
// Ruta de prueba principal (Fuera de la función para mayor orden)
app.get("/casaalcatraz", (req, res) => {
    res.json({
        ok: true,
        message: "¡Servidor de Casa Alcatraz funcionando correctamente!",
    });
});
// Función asíncrona para inicializar servicios y arrancar el servidor
const startApplication = async () => {
    try {
        // 1. Inicializa la infraestructura compartida (BD y Tablas)
        await (0, db_init_1.initializeDatabase)();
        app.use("/casaalcatraz/api/v1", app_routes_1.appRouter);
        app.listen(PORT, () => {
            console.log(`🚀 Servidor corriendo en: http://localhost:${PORT}`);
        });
    }
    catch (error) {
        console.error("❌ No se pudo arrancar la aplicación:", error);
        process.exit(1); // Detiene todo si la infraestructura falla
    }
};
// Ejecutamos el arranque
startApplication();
