import { Router } from "express";
import { ReservasRepository } from "./database/reservas.repository";
import { CrearReservaUseCase } from "../application/crearReserva.usecase";
import { BuscarReservaPorIdUseCase } from "../application/buscarReservaPorId.usecase";
import { ListarReservasUseCase } from "../application/listarReservasUseCase";
import { ReservasController } from "./reservas.controller";
import { OrigenReservasRepository } from "./database/origenReserva.repository";
import { ObtenerOrigenesReservaUseCase } from "../application/obtenerOrigenesReserva.usecase";
import { ObtenerFechasOcupadasUseCase } from "../application/obtener-fechas-ocupadas.usecase";
import { FechasOcupadasRepository } from "./database/fechas.repository";

const reservasRouter = Router();

const reservasRepository = new ReservasRepository();
const origenReservasRepository = new OrigenReservasRepository();
const fechasOcupadasRepository = new FechasOcupadasRepository();
const listarReservasUseCase = new ListarReservasUseCase(reservasRepository);
const buscarReservaPorIdUseCase = new BuscarReservaPorIdUseCase(
  reservasRepository,
);
const crearReservaUseCase = new CrearReservaUseCase(reservasRepository);
const obtenerOrigenesReservaUseCase = new ObtenerOrigenesReservaUseCase(
  origenReservasRepository,
);
const obtenerFechasOcupadasUseCase = new ObtenerFechasOcupadasUseCase(
  fechasOcupadasRepository,
);

const reservasController = new ReservasController(
  listarReservasUseCase,
  buscarReservaPorIdUseCase,
  crearReservaUseCase,
  obtenerOrigenesReservaUseCase,
  obtenerFechasOcupadasUseCase,
);
reservasRouter.get(
  "/todas",
  reservasController.obtenerTodasLasReservas.bind(reservasController),
);
reservasRouter.get(
  "/origenes",
  reservasController.obtenerOrigenesReserva.bind(reservasController),
);
reservasRouter.post(
  "/crear-reserva",
  reservasController.crearReserva.bind(reservasController),
);
reservasRouter.get(
  "/ocupadas",
  reservasController.obtenerFechasOcupadas.bind(reservasController),
);
reservasRouter.get(
  "/:id",
  reservasController.obtenerReservaPorId.bind(reservasController),
);

export { reservasRouter };
