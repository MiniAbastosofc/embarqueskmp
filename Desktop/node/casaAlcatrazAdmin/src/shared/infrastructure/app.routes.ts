import { Router } from "express";
import { authRouter } from "../../features/auth/infrastructure/auth.routes";
import { reservasRouter } from "../../features/reservas/infrastructure/reservas.routes";
import { ingresosRouter } from "../../features/ingresos/infrastructure/ingresos.routes";
import { resumenRouter } from "../../features/resumen/infrastructure/resumen.routes";
// Aquí irás importando las rutas de tus otros módulos en el futuro:
// import { incomeRouter } from "../../features/income/infrastructure/income.routes";

const appRouter = Router();

// Acoplamos las rutas de cada feature con un prefijo limpio
appRouter.use("/auth", authRouter);
appRouter.use("/reservas", reservasRouter);
appRouter.use("/ingresos", ingresosRouter);
appRouter.use("/resumen", resumenRouter);
// appRouter.use("/income", incomeRouter);
// appRouter.use("/booking", bookingRouter);

export { appRouter };
