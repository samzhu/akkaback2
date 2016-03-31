package com.sam.data

import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicInteger

import com.hazelcast.config.Config
import com.hazelcast.core.{Hazelcast, HazelcastInstance}
import com.sam.comm.Posts

/**
  * Created by SAM on 2016/3/30.
  */

object HazelcastStore {
  val config = new Config();
  val hInstance = Hazelcast.newHazelcastInstance(config);
  val counter = new AtomicInteger
}

trait HazelcastStore {
  /**
    * 取得資料儲存庫
    *
    * @return
    */
  def getInstance() = {
    HazelcastStore.hInstance
  }

  def getPosts(postid: String): Either[String, Posts] = {
    try {
      val map: ConcurrentMap[String, Posts] = HazelcastStore.hInstance.getMap[String, Posts]("posts")
      val posts: Posts = map.get(postid)
      if (posts != null) {
        Right(posts)
      } else {
        Left("Resource does not exist!")
      }
    } catch {
      case e: Exception => Left("Get resource failed!")
    }
  }

  def savePosts(posts: Posts): Either[String, String] = {
    var postid = ""
    try {
      val map: ConcurrentMap[String, Posts] = HazelcastStore.hInstance.getMap[String, Posts]("posts")
      postid = HazelcastStore.counter.incrementAndGet().toString
      map.put(postid, posts)
      Right(postid)
    } catch {
      case e: Exception => Left("Save failed!")
    }
  }

}
