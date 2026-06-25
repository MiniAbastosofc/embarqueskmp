"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ObtenerDashboardCompletoUseCase = void 0;
class ObtenerDashboardCompletoUseCase {
    resumenRepository;
    constructor(resumenRepository) {
        this.resumenRepository = resumenRepository;
    }
    async execute() {
        const dashboardData = await this.resumenRepository.obtenerDashboardCompleto();
        return {
            success: true,
            data: dashboardData,
        };
    }
}
exports.ObtenerDashboardCompletoUseCase = ObtenerDashboardCompletoUseCase;
