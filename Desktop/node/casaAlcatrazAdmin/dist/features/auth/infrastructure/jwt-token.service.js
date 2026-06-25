"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.JwtTokenService = void 0;
const jsonwebtoken_1 = __importDefault(require("jsonwebtoken"));
class JwtTokenService {
    secrectKey = process.env.JWT_SECRET || "default_secret_key";
    generateToken(payload) {
        return jsonwebtoken_1.default.sign(payload, this.secrectKey, { expiresIn: "30d" });
    }
}
exports.JwtTokenService = JwtTokenService;
