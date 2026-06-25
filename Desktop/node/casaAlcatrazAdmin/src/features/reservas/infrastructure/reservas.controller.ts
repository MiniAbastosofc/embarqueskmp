import { Request, Response } from "express";
import { ListarReservasUseCase } from "../application/listarReservasUseCase";
import { BuscarReservaPorIdUseCase } from "../application/buscarReservaPorId.usecase";
import { CrearReservaUseCase } from "../application/crearReserva.usecase";
import { ObtenerOrigenesReservaUseCase } from "../application/obtenerOrigenesReserva.usecase";
import { ObtenerFechasOcupadasUseCase } from "../application/obtener-fechas-ocupadas.usecase";

export class ReservasController {
  // Inyectamos los 3 casos de uso independientes en el constructor
  constructor(
    private listarReservasUseCase: ListarReservasUseCase,
    private obtenerReservaPorIdUseCase: BuscarReservaPorIdUseCase,
    private crearReservaUseCase: CrearReservaUseCase,
    private obtenerOrigenesReservaUseCase: ObtenerOrigenesReservaUseCase,
    private obtenerFechasOcupadasUseCase: ObtenerFechasOcupadasUseCase,
  ) {}

  async obtenerTodasLasReservas(req: Request, res: Response): Promise<void> {
    try {
      const result = await this.listarReservasUseCase.execute();

      res.status(200).json(result);
    } catch (error) {
      res.status(500).json({
        success: false,
        error: (error as Error).message || "Error al obtener las reservas",
      });
    }
  }

  async obtenerReservaPorId(req: Request, res: Response): Promise<void> {
    try {
      const id = parseInt(req.params.id as string);

      if (!id || isNaN(id)) {
        res.status(400).json({
          success: false,
          error: "El id de la reserva debe ser un número válido y es requerido",
        });
        return;
      }

      const result = await this.obtenerReservaPorIdUseCase.execute(id);
      res.status(200).json(result);
    } catch (error) {
      res.status(404).json({
        success: false,
        error: (error as Error).message || "Error al obtener la reserva",
      });
    }
  }

  async crearReserva(req: Request, res: Response): Promise<void> {
    try {
      const {
        usuarioId,
        nombreCliente,
        apellidoCliente,
        telefono,
        email,
        fechaInicio,
        fechaFin,
        totalDiasReservacion,
        montoTotal,
        origenReservaId,
        nuevoOrigenNombre,
        personasHospedadas,
        extras,
      } = req.body;
      if (
        !usuarioId ||
        !nombreCliente ||
        !apellidoCliente ||
        !telefono ||
        !fechaInicio ||
        !fechaFin ||
        !montoTotal ||
        origenReservaId === undefined ||
        origenReservaId === null ||
        origenReservaId === "" ||
        !personasHospedadas
      ) {
        res.status(400).json({
          success: false,
          error: "Faltan campos obligatorios para registrar la reservación",
        });
        return;
      }

      const esOtro = origenReservaId === "OTRO";

      const result = await this.crearReservaUseCase.execute({
        usuarioId: Number(usuarioId),
        nombreCliente,
        apellidoCliente,
        telefono: telefono || null,
        email: email || null,
        fechaInicio: new Date(fechaInicio),
        fechaFin: new Date(fechaFin),
        totalDiasReservacion: Number(totalDiasReservacion),
        montoTotal: Number(montoTotal),
        origenReservaId: esOtro ? 0 : Number(origenReservaId),
        nuevoOrigenNombre: esOtro ? String(nuevoOrigenNombre) : undefined,
        personasHospedadas: Number(personasHospedadas),
        extras: Array.isArray(extras) ? extras : [],
      });
      res.status(201).json(result);
    } catch (error) {
      res.status(400).json({
        success: false,
        error: (error as Error).message || "Error al crear la reservación",
      });
    }
  }
  async obtenerOrigenesReserva(req: Request, res: Response): Promise<void> {
    try {
      const result = await this.obtenerOrigenesReservaUseCase.execute();
      res.status(200).json(result);
    } catch (error) {
      res.status(500).json({
        success: false,
        error:
          (error as Error).message ||
          "Error al obtener el catálogo de orígenes de reserva",
      });
    }
  }

  async obtenerFechasOcupadas(req: Request, res: Response): Promise<void> {
    try {
      const result = await this.obtenerFechasOcupadasUseCase.execute();

      res.status(200).json(result);
    } catch (error) {
      res.status(500).json({
        success: false,
        error:
          (error as Error).message || "Error al obtener las fechas ocupadas",
      });
    }
  }
}
