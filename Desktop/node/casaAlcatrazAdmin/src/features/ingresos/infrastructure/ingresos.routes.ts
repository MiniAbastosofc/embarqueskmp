import { Router } from "express";
import { IngresosRepository } from "./database/ingresos.repository";
import { CrearIngresoUsecase } from "../application/crearIngreso.usecase";
import { IngresosController } from "./ingresos.controller";
import { BuscarPagosPorReservaUseCase } from "../application/detallePagosXCliente.usecase";

const ingresosRouter = Router();

const ingresosRepository = new IngresosRepository();
const crearIngresoUseCase = new CrearIngresoUsecase(ingresosRepository);
const buscarPagosPorReservaUseCase = new BuscarPagosPorReservaUseCase(
  ingresosRepository,
);

const ingresosController = new IngresosController(
  crearIngresoUseCase,
  buscarPagosPorReservaUseCase,
);

ingresosRouter.post(
  "/crear-ingreso",
  ingresosController.insertarIngreso.bind(ingresosController),
);
ingresosRouter.get(
  "/:id",
  ingresosController.obtenerPagosPorReserva.bind(ingresosController),
);

export { ingresosRouter };
