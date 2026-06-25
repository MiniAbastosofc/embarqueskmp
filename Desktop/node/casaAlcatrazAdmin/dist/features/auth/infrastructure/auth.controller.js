"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.AuthController = void 0;
class AuthController {
    loginUsCase;
    constructor(loginUsCase) {
        this.loginUsCase = loginUsCase;
    }
    async login(req, res) {
        try {
            const { username, password } = req.body;
            if (!username || !password) {
                res.status(400).json({ error: "Usuario y contraseña son requeridos" });
                return;
            }
            const result = await this.loginUsCase.execute(username, password);
            res.status(200).json(result);
        }
        catch (error) {
            res
                .status(401)
                .json({ error: error.message || "Error de autenticación" });
        }
    }
}
exports.AuthController = AuthController;
