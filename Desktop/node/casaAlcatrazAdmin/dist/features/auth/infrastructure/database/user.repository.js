"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.UserRepository = void 0;
const mssql_1 = __importDefault(require("mssql"));
const database_1 = require("../../../../config/database");
class UserRepository {
    async findByUsername(username) {
        let pool;
        try {
            pool = await mssql_1.default.connect(database_1.dbConfig);
            const result = await pool
                .request()
                .input("user", mssql_1.default.VarChar, username)
                .query("SELECT id, [user], password, rol_id, activo FROM AAC_Alcatraz_usuarios WHERE [user] = @user");
            if (result.recordset.length === 0) {
                return null;
            }
            const row = result.recordset[0];
            return {
                id: row.id,
                username: row.user,
                password: row.password,
                roleId: row.rol_id,
                isActive: row.activo,
            };
        }
        catch (error) { }
        return null;
    }
}
exports.UserRepository = UserRepository;
