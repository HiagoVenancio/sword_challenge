import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.hrv.swordchallenge.Mocks
import com.hrv.swordchallenge.Mocks.initialBreeds
import com.hrv.swordchallenge.Mocks.newBreeds
import com.hrv.swordchallenge.Mocks.searchResult
import com.hrv.swordchallenge.data.CatRepository
import com.hrv.swordchallenge.ui.MainViewModel
import com.hrv.swordchallenge.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val repository = mock<CatRepository>()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun fetchFavoriteCatBreeds() = testDispatcher.runBlockingTest {
        doReturn(flow { emit(Resource.Success(Mocks.favoriteBreeds)) })
            .`when`(repository).getFavoriteCatBreeds()

        viewModel.fetchFavoriteCatBreeds()

        assertThat(viewModel.favorites.value.data).isEqualTo(Mocks.favoriteBreeds)
    }

    @Test
    fun refreshCatBreeds() = testDispatcher.runBlockingTest {
        doReturn(flow { emit(Resource.Success(initialBreeds)) })
            .`when`(repository).getCatsFromDatabase()

        viewModel.refreshCatBreeds()

        assertThat(viewModel.catBreeds.value.data).isEqualTo(initialBreeds)
    }

    @Test
    fun loadMoreCatBreeds() = testDispatcher.runBlockingTest {

        doReturn(flow { emit(Resource.Success(initialBreeds)) })
            .`when`(repository).getCatBreeds()

        viewModel.fetchCatBreeds()

        doReturn(flow { emit(Resource.Success(newBreeds)) })
            .`when`(repository).loadMoreCatBreeds(1)

        viewModel.loadMoreCatBreeds()

        val expectedBreeds = initialBreeds + newBreeds

        assertThat(viewModel.catBreeds.value.data).isEqualTo(expectedBreeds)
    }

    @Test
    fun updateSearchQuery() = testDispatcher.runBlockingTest {
        doReturn(flow { emit(Resource.Success(searchResult)) })
            .`when`(repository).searchCatBreeds("Breed 2")

        viewModel.updateSearchQuery("Breed 2")

        assertThat(viewModel.catBreeds.value.data).isEqualTo(searchResult)
    }
}
