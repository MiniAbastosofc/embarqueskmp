"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ResumenUseCase = void 0;
class ResumenUseCase {
    resumenRepository;
    constructor(resumenRepository) {
        this.resumenRepository = resumenRepository;
    }
    async execute() {
        const resumenData = await this.resumenRepository.obtenerResumenDashboard();
        return {
            success: true,
            data: resumenData,
        };
    }
}
exports.ResumenUseCase = ResumenUseCase;
