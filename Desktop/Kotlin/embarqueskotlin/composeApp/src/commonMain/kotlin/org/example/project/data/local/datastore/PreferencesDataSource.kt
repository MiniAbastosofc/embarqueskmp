package org.example.project.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import org.example.project.domain.model.LoginModel

class PreferencesDataSource(private val dataStore: DataStore<Preferences>) {

    private companion object {
        val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
        val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
        val USER_ID_KEY = intPreferencesKey("user_id")
        val USER_NAME_KEY = stringPreferencesKey("user_name")

        // NUEVAS KEYS para los datos adicionales
        val USER_DATA_KEY = stringPreferencesKey("user_data")
        val USER_ROLE_KEY = intPreferencesKey("user_role")
        val USER_FULL_NAME_KEY = stringPreferencesKey("user_full_name")
        val EMPLOYEE_NUMBER_KEY = stringPreferencesKey("employee_number")
        val USER_ACTIVE_KEY = booleanPreferencesKey("user_active")
        val CREATION_DATE_KEY = stringPreferencesKey("creation_date")
    }

    suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    fun getAuthToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[AUTH_TOKEN_KEY]
        }
    }

    suspend fun saveLoginState(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN_KEY] = isLoggedIn
        }
    }

    fun getLoginState(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN_KEY] ?: false
        }
    }

    suspend fun saveUserId(userId: Int) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    fun getUserId(): Flow<Int?> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID_KEY]
        }
    }

    suspend fun saveUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = userName
        }
    }

    fun getUserName(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USER_NAME_KEY]
        }
    }

    // NUEVOS MÉTODOS IMPLEMENTADOS:

    suspend fun saveUserData(loginModel: LoginModel) {
        val userJson = Json.encodeToString(LoginModel.serializer(), loginModel)
        dataStore.edit { preferences ->
            preferences[USER_DATA_KEY] = userJson
            preferences[USER_ID_KEY] = loginModel.UsuarioId
            preferences[USER_NAME_KEY] = loginModel.Usuario
            preferences[USER_ROLE_KEY] = loginModel.RolID
            preferences[USER_FULL_NAME_KEY] = "${loginModel.Nombre} ${loginModel.Apellido}"
            preferences[EMPLOYEE_NUMBER_KEY] = loginModel.NumeroEmpleado
            preferences[USER_ACTIVE_KEY] = loginModel.Activo
            preferences[CREATION_DATE_KEY] = loginModel.FechaCreacion
            preferences[IS_LOGGED_IN_KEY] = true
            preferences[AUTH_TOKEN_KEY] = "authenticated_${loginModel.Usuario}"
        }
    }

    suspend fun saveUserRole(roleId: Int) {
        dataStore.edit { preferences ->
            preferences[USER_ROLE_KEY] = roleId
        }
    }

    suspend fun saveUserFullName(fullName: String) {
        dataStore.edit { preferences ->
            preferences[USER_FULL_NAME_KEY] = fullName
        }
    }

    suspend fun saveEmployeeNumber(employeeNumber: String) {
        dataStore.edit { preferences ->
            preferences[EMPLOYEE_NUMBER_KEY] = employeeNumber
        }
    }

    suspend fun saveUserActive(active: Boolean) {
        dataStore.edit { preferences ->
            preferences[USER_ACTIVE_KEY] = active
        }
    }

    suspend fun saveCreationDate(creationDate: String) {
        dataStore.edit { preferences ->
            preferences[CREATION_DATE_KEY] = creationDate
        }
    }

    fun getUserData(): Flow<LoginModel?> {
        return dataStore.data.map { preferences ->
            preferences[USER_DATA_KEY]?.let { json ->
                try {
                    Json.decodeFromString(LoginModel.serializer(), json)
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    fun getUserRole(): Flow<Int?> {
        return dataStore.data.map { preferences ->
            preferences[USER_ROLE_KEY]
        }
    }

    fun getUserFullName(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USER_FULL_NAME_KEY]
        }
    }

    fun getEmployeeNumber(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[EMPLOYEE_NUMBER_KEY]
        }
    }

    fun getUserActive(): Flow<Boolean?> {
        return dataStore.data.map { preferences ->
            preferences[USER_ACTIVE_KEY]
        }
    }

    fun getCreationDate(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[CREATION_DATE_KEY]
        }
    }

    // También actualiza el clearSession para limpiar las nuevas keys
    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN_KEY)
            preferences.remove(IS_LOGGED_IN_KEY)
            preferences.remove(USER_ID_KEY)
            preferences.remove(USER_NAME_KEY)
            // Limpiar las nuevas keys también
            preferences.remove(USER_DATA_KEY)
            preferences.remove(USER_ROLE_KEY)
            preferences.remove(USER_FULL_NAME_KEY)
            preferences.remove(EMPLOYEE_NUMBER_KEY)
            preferences.remove(USER_ACTIVE_KEY)
            preferences.remove(CREATION_DATE_KEY)
        }
    }
}