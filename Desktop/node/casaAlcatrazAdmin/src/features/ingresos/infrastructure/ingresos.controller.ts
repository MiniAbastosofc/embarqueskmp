import { Request, Response } from "express";
import {
  CrearIngresoResponse,
  CrearIngresoUsecase,
} from "../application/crearIngreso.usecase";
import { BuscarPagosPorReservaUseCase } from "../application/detallePagosXCliente.usecase";

export class IngresosController {
  constructor(
    private crearIngresoUseCase: CrearIngresoUsecase,
    private buscarPagosPorReservaUseCase: BuscarPagosPorReservaUseCase,
  ) {}

  async insertarIngreso(req: Request, res: Response): Promise<void> {
    try {
      const {
        origenDineroId,
        reservacionId,
        conceptoPagoId,
        montoBruto,
        aplicaComision,
        montoComision,
        userCreate,
      } = req.body;

      if (
        !origenDineroId ||
        !reservacionId ||
        !conceptoPagoId ||
        montoBruto === undefined ||
        aplicaComision === undefined ||
        montoComision === undefined ||
        !userCreate
      ) {
        res.status(400).json({
          success: false,
          error:
            "Faltan campos obligatorios para poder registrar el ingreso/pago",
        });
        return;
      }
      const result = await this.crearIngresoUseCase.execute({
        origenDineroId: Number(origenDineroId),
        reservacionId: Number(reservacionId),
        conceptoPagoId: Number(conceptoPagoId),
        montoBruto: Number(montoBruto),
        aplicaComision: Boolean(aplicaComision),
        montoComision: Number(montoComision),
        userCreate: Number(userCreate),
      });

      res.status(201).json(result);
    } catch (error) {
      res.status(400).json({
        success: false,
        error:
          (error as Error).message ||
          "Error al procesar el registro del ingreso",
      });
    }
  }
  async obtenerPagosPorReserva(req: Request, res: Response): Promise<void> {
    try {
      const reservacionId = parseInt(`${req.params.id}`);
      if (!reservacionId || isNaN(reservacionId)) {
        res.status(400).json({
          success: false,
          error:
            "El id de la reservación debe ser un número válido y es requerido",
        });
        return;
      }
      const result =
        await this.buscarPagosPorReservaUseCase.execute(reservacionId);
      res.status(200).json(result);
    } catch (error) {
      res.status(500).json({
        success: false,
        error:
          (error as Error).message || "Error al obtener el historial de pagos",
      });
    }
  }
}
