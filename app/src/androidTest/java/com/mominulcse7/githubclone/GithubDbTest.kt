package com.mominulcse7.githubclone

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mominulcse7.githubclone.db.GithubDB
import com.mominulcse7.githubclone.db.GithubDao
import com.mominulcse7.githubclone.model.OwnerModel
import com.mominulcse7.githubclone.model.RepositoryModel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class GithubDbTest {

    lateinit var db: GithubDB
    lateinit var dao: GithubDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GithubDB::class.java).build()
        dao = db.getGithubDao()
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun isSearchParametersAreValid(): Unit = runBlocking {
        val searchKey = ""
        val sortBy = ""
        val orderBy = ""
        val list = dao.getRepositoryListDB(searchKey, sortBy, orderBy)

        assertEquals(list.isNullOrEmpty(), true)
    }

    @Test
    fun saveWithNullRepositoryId_ShouldReturnFalse(): Unit = runBlocking {

        val repositoryModel = RepositoryModel()
        repositoryModel.id = null

        val listToSave = ArrayList<RepositoryModel>()
        listToSave.add(repositoryModel)
        dao.addGithubRepoList(listToSave)

        val searchKey = ""
        val sortBy = "stars"
        val orderBy = "asc"
        val listRec = dao.getRepositoryListDB(searchKey, sortBy, orderBy)

        var isExist = false
        if (listRec != null && listRec.isNotEmpty())
            for (repo in listRec)
                if (repo.id == null || repo.id == repositoryModel.id) {
                    isExist = true
                    break
                }

        assertEquals(isExist, false)
    }

    @Test
    fun saveWithValidRepositoryId_ShouldReturnTrue(): Unit = runBlocking {

        val repositoryModel = RepositoryModel()
        repositoryModel.id = 10

        val listToSave = ArrayList<RepositoryModel>()
        listToSave.add(repositoryModel)
        dao.addGithubRepoList(listToSave)

        val searchKey = ""
        val sortBy = "stars"
        val orderBy = "asc"
        val listRec = dao.getRepositoryListDB(searchKey, sortBy, orderBy)

        var isExist = false
        if (listRec != null && listRec.isNotEmpty())
            for (repo in listRec)
                if (repo.id == null || repo.id == repositoryModel.id) {
                    isExist = true
                    break
                }

        assertEquals(isExist, true)
    }

    @Test
    fun isGettingDataFromRoomIsWorking(): Unit = runBlocking {

        val repositoryModel = RepositoryModel()
        repositoryModel.id = 10

        val listToSave = ArrayList<RepositoryModel>()
        listToSave.add(repositoryModel)
        dao.addGithubRepoList(listToSave)

        val searchKey = ""
        val sortBy = "stars"
        val orderBy = "asc"
        val listRec = dao.getRepositoryListDB(searchKey, sortBy, orderBy)

        var isExist = false
        if (listRec != null && listRec.isNotEmpty())
            for (repo in listRec)
                if (repo.id != null && repo.id == repositoryModel.id) {
                    isExist = true
                    break
                }

        assertEquals(isExist, true)
    }
}