"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.CrearIngresoUsecase = void 0;
class CrearIngresoUsecase {
    ingresoRepository;
    constructor(ingresoRepository) {
        this.ingresoRepository = ingresoRepository;
    }
    async execute(input) {
        if (input.montoBruto <= 0) {
            throw new Error("El monto bruto del pago debe ser mayor a 0");
        }
        if (input.aplicaComision && input.montoComision <= 0) {
            throw new Error("Si aplica comisión, el monto de la comisión no puede ser 0");
        }
        await this.ingresoRepository.create(input);
        return {
            success: true,
            message: "El pago a sido registrado correctamente",
        };
    }
}
exports.CrearIngresoUsecase = CrearIngresoUsecase;
