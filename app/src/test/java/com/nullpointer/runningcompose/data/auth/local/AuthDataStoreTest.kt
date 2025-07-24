import androidx.datastore.preferences.core.edit
import com.nullpointer.runningcompose.data.auth.local.AuthDataStore
import com.nullpointer.runningcompose.data.mocks.DataStoreMock
import com.nullpointer.runningcompose.models.data.AuthData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AuthDataStoreTest {

    private val dataStore = DataStoreMock()
    private val authDataStore = AuthDataStore(dataStore)

    @Before
    fun setUp() {
        runBlocking {
            dataStore.edit { it.clear() }
        }
    }


    @Test
    fun `saveAuthData correctly saves AuthData`() = runBlocking {
        // * create an instance of AuthData
        val authData = AuthData(name = "John Doe", weight = 70F)

        // * save the AuthData
        authDataStore.saveAuthData(authData)

        // * retrieve the AuthData
        val response = authDataStore.getAuthData().first()

        // * assert that the retrieved AuthData is the same as the one saved
        assertEquals(authData, response)
    }

    @Test
    fun `return null when no AuthData is saved`() = runBlocking {
        // * retrieve the AuthData
        val response = authDataStore.getAuthData().first()

        // * assert that the retrieved AuthData is null
        assertEquals(null, response)
    }

}