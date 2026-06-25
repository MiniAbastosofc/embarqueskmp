"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ResumenController = void 0;
class ResumenController {
    resumenUseCase;
    obtenerDashboardUseCase;
    constructor(resumenUseCase, obtenerDashboardUseCase) {
        this.resumenUseCase = resumenUseCase;
        this.obtenerDashboardUseCase = obtenerDashboardUseCase;
    }
    async obtenerResumen(req, res) {
        try {
            const result = await this.resumenUseCase.execute();
            res.status(200).json(result);
        }
        catch (error) {
            res.status(400).json({
                success: false,
                error: error.message ||
                    "Error al generar el resumen del dashboard",
            });
        }
    }
    async obtenerResumenCompleto(req, res) {
        try {
            // 3. Ejecutamos la acción (el caso de uso disparará los 5 queries en paralelo)
            const result = await this.obtenerDashboardUseCase.execute();
            // 4. Respondemos con éxito enviando el JSON consolidado
            res.status(200).json(result);
        }
        catch (error) {
            // 5. Cambiado a 500 porque si los queries fallan suele ser un error interno del servidor o BD
            res.status(500).json({
                success: false,
                error: error.message ||
                    "Error al generar las métricas completas del dashboard",
            });
        }
    }
}
exports.ResumenController = ResumenController;
