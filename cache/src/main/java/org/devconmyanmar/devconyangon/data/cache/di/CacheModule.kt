package org.devconmyanmar.devconyangon.data.cache.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Binds
import dagger.Module
import dagger.Provides
import org.devconmyanmar.devconyangon.DevConYangonDb
import org.devconmyanmar.devconyangon.data.cache.FavoriteSessionTable
import org.devconmyanmar.devconyangon.data.cache.RoomTable
import org.devconmyanmar.devconyangon.data.cache.SessionCacheDataSourceImpl
import org.devconmyanmar.devconyangon.data.cache.SessionSpeakerTable
import org.devconmyanmar.devconyangon.data.cache.SessionTable
import org.devconmyanmar.devconyangon.data.cache.SpeakerCacheDataSourceImpl
import org.devconmyanmar.devconyangon.data.cache.SpeakerTable
import org.devconmyanmar.devconyangon.data.cache.columnadapter.LocalDateColumnAdapter
import org.devconmyanmar.devconyangon.data.cache.columnadapter.LocalTimeColumnAdapter
import org.devconmyanmar.devconyangon.data.cache.columnadapter.RoomIdColumnAdapter
import org.devconmyanmar.devconyangon.data.cache.columnadapter.SessionIdColumnAdapter
import org.devconmyanmar.devconyangon.data.cache.columnadapter.SpeakerIdColumnAdapter
import org.devconmyanmar.devconyangon.data.datasource.SessionCacheDataSource
import org.devconmyanmar.devconyangon.data.datasource.SpeakerCacheDataSource
import javax.inject.Singleton

/**
 * Created by Vincent on 2019-06-20
 */
@Module(includes = [CacheModule.Providers::class])
abstract class CacheModule {

  @Binds
  abstract fun sessionCacheDataSource(sessionCacheDataSource: SessionCacheDataSourceImpl): SessionCacheDataSource

  @Binds
  abstract fun speakerCacheDataSource(speakerCacheDataSource: SpeakerCacheDataSourceImpl) : SpeakerCacheDataSource

  @Module
  internal object Providers {

    @Provides
    @Singleton
    fun sqlDriver(context: Context): SqlDriver {
      return AndroidSqliteDriver(DevConYangonDb.Schema, context, "devcon_ygn.db")
    }

    @Provides
    @Singleton
    fun database(sqlDriver: SqlDriver): DevConYangonDb {

      val roomTableAdapter = RoomTable.Adapter(RoomIdColumnAdapter)

      val speakerTableAdapter = SpeakerTable.Adapter(SpeakerIdColumnAdapter)

      val sessionTableAdapter = SessionTable.Adapter(
        SessionIdColumnAdapter,
        LocalDateColumnAdapter,
        LocalTimeColumnAdapter,
        LocalTimeColumnAdapter,
        RoomIdColumnAdapter
      )

      val sessionSpeakerTableAdapter =
        SessionSpeakerTable.Adapter(SessionIdColumnAdapter, SpeakerIdColumnAdapter)

      val favoriteSessionTableAdapter = FavoriteSessionTable.Adapter(SessionIdColumnAdapter)

      return DevConYangonDb(
        sqlDriver,
        favoriteSessionTableAdapter,
        roomTableAdapter,
        sessionSpeakerTableAdapter,
        sessionTableAdapter,
        speakerTableAdapter
      )
    }

    @Provides
    fun sharedPref(context: Context): SharedPreferences {
      return PreferenceManager.getDefaultSharedPreferences(context)
    }

  }

}