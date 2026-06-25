"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.dbConfig = void 0;
exports.dbConfig = {
    user: process.env.DB_USER || 'sa',
    password: process.env.DB_PASSWORD || 'TuPasswordSeguro123',
    server: process.env.DB_SERVER || 'localhost',
    database: process.env.DB_NAME || 'MiBaseDatos',
    options: {
        encrypt: false,
        trustServerCertificate: true,
    },
    pool: { max: 10, min: 0, idleTimeoutMillis: 30000 }
};
