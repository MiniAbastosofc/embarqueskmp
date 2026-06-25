"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ReservasController = void 0;
class ReservasController {
    listarReservasUseCase;
    obtenerReservaPorIdUseCase;
    crearReservaUseCase;
    obtenerOrigenesReservaUseCase;
    obtenerFechasOcupadasUseCase;
    // Inyectamos los 3 casos de uso independientes en el constructor
    constructor(listarReservasUseCase, obtenerReservaPorIdUseCase, crearReservaUseCase, obtenerOrigenesReservaUseCase, obtenerFechasOcupadasUseCase) {
        this.listarReservasUseCase = listarReservasUseCase;
        this.obtenerReservaPorIdUseCase = obtenerReservaPorIdUseCase;
        this.crearReservaUseCase = crearReservaUseCase;
        this.obtenerOrigenesReservaUseCase = obtenerOrigenesReservaUseCase;
        this.obtenerFechasOcupadasUseCase = obtenerFechasOcupadasUseCase;
    }
    async obtenerTodasLasReservas(req, res) {
        try {
            const result = await this.listarReservasUseCase.execute();
            res.status(200).json(result);
        }
        catch (error) {
            res.status(500).json({
                success: false,
                error: error.message || "Error al obtener las reservas",
            });
        }
    }
    async obtenerReservaPorId(req, res) {
        try {
            const id = parseInt(req.params.id);
            if (!id || isNaN(id)) {
                res.status(400).json({
                    success: false,
                    error: "El id de la reserva debe ser un número válido y es requerido",
                });
                return;
            }
            const result = await this.obtenerReservaPorIdUseCase.execute(id);
            res.status(200).json(result);
        }
        catch (error) {
            res.status(404).json({
                success: false,
                error: error.message || "Error al obtener la reserva",
            });
        }
    }
    async crearReserva(req, res) {
        try {
            const { usuarioId, nombreCliente, apellidoCliente, telefono, email, fechaInicio, fechaFin, totalDiasReservacion, montoTotal, origenReservaId, nuevoOrigenNombre, personasHospedadas, extras, } = req.body;
            if (!usuarioId ||
                !nombreCliente ||
                !apellidoCliente ||
                !telefono ||
                !fechaInicio ||
                !fechaFin ||
                !montoTotal ||
                origenReservaId === undefined ||
                origenReservaId === null ||
                origenReservaId === "" ||
                !personasHospedadas) {
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
        }
        catch (error) {
            res.status(400).json({
                success: false,
                error: error.message || "Error al crear la reservación",
            });
        }
    }
    async obtenerOrigenesReserva(req, res) {
        try {
            const result = await this.obtenerOrigenesReservaUseCase.execute();
            res.status(200).json(result);
        }
        catch (error) {
            res.status(500).json({
                success: false,
                error: error.message ||
                    "Error al obtener el catálogo de orígenes de reserva",
            });
        }
    }
    async obtenerFechasOcupadas(req, res) {
        try {
            const result = await this.obtenerFechasOcupadasUseCase.execute();
            res.status(200).json(result);
        }
        catch (error) {
            res.status(500).json({
                success: false,
                error: error.message || "Error al obtener las fechas ocupadas",
            });
        }
    }
}
exports.ReservasController = ReservasController;
