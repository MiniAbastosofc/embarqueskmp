"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.LoginUseCase = void 0;
class LoginUseCase {
    userRepository;
    tokenService;
    constructor(userRepository, tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }
    async execute(username, password) {
        const user = await this.userRepository.findByUsername(username);
        if (!user) {
            throw new Error("Usuario no encontrado");
        }
        if (user.password !== password) {
            throw new Error("Contraseña incorrecta, vuelve a intentarlo");
        }
        if (!user.isActive) {
            throw new Error("Usuario inactivo, contacta al administrador");
        }
        const token = this.tokenService.generateToken({
            id: user.id,
            username: user.username,
            roleId: user.roleId,
        });
        return {
            success: true,
            id: user.id,
            username: user.username,
            roleId: user.roleId,
            isActive: user.isActive,
            token,
        };
    }
}
exports.LoginUseCase = LoginUseCase;
