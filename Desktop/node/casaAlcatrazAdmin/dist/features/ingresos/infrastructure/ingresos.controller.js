"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.IngresosController = void 0;
class IngresosController {
    crearIngresoUseCase;
    buscarPagosPorReservaUseCase;
    constructor(crearIngresoUseCase, buscarPagosPorReservaUseCase) {
        this.crearIngresoUseCase = crearIngresoUseCase;
        this.buscarPagosPorReservaUseCase = buscarPagosPorReservaUseCase;
    }
    async insertarIngreso(req, res) {
        try {
            const { origenDineroId, reservacionId, conceptoPagoId, montoBruto, aplicaComision, montoComision, userCreate, } = req.body;
            if (!origenDineroId ||
                !reservacionId ||
                !conceptoPagoId ||
                montoBruto === undefined ||
                aplicaComision === undefined ||
                montoComision === undefined ||
                !userCreate) {
                res.status(400).json({
                    success: false,
                    error: "Faltan campos obligatorios para poder registrar el ingreso/pago",
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
        }
        catch (error) {
            res.status(400).json({
                success: false,
                error: error.message ||
                    "Error al procesar el registro del ingreso",
            });
        }
    }
    async obtenerPagosPorReserva(req, res) {
        try {
            const reservacionId = parseInt(`${req.params.id}`);
            if (!reservacionId || isNaN(reservacionId)) {
                res.status(400).json({
                    success: false,
                    error: "El id de la reservación debe ser un número válido y es requerido",
                });
                return;
            }
            const result = await this.buscarPagosPorReservaUseCase.execute(reservacionId);
            res.status(200).json(result);
        }
        catch (error) {
            res.status(500).json({
                success: false,
                error: error.message || "Error al obtener el historial de pagos",
            });
        }
    }
}
exports.IngresosController = IngresosController;
