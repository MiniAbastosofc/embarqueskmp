"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.appRouter = void 0;
const express_1 = require("express");
const auth_routes_1 = require("../../features/auth/infrastructure/auth.routes");
const reservas_routes_1 = require("../../features/reservas/infrastructure/reservas.routes");
const ingresos_routes_1 = require("../../features/ingresos/infrastructure/ingresos.routes");
const resumen_routes_1 = require("../../features/resumen/infrastructure/resumen.routes");
// Aquí irás importando las rutas de tus otros módulos en el futuro:
// import { incomeRouter } from "../../features/income/infrastructure/income.routes";
const appRouter = (0, express_1.Router)();
exports.appRouter = appRouter;
// Acoplamos las rutas de cada feature con un prefijo limpio
appRouter.use("/auth", auth_routes_1.authRouter);
appRouter.use("/reservas", reservas_routes_1.reservasRouter);
appRouter.use("/ingresos", ingresos_routes_1.ingresosRouter);
appRouter.use("/resumen", resumen_routes_1.resumenRouter);
