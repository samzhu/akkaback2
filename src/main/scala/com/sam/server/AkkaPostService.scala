package com.sam.server

import akka.actor.{ActorSystem, Props}
import com.sam.actor.PostsActor
import com.sam.data.HazelcastStore
import com.typesafe.config.ConfigFactory

/**
  * Created by SAM on 2016/3/30.
  */
object AkkaPostService extends App with HazelcastStore{
  val config = ConfigFactory.parseString("akka.cluster.roles = [backend]").
    withFallback(ConfigFactory.load())

  val system = ActorSystem("ClusterSystem", config)
  system.actorOf(Props[PostsActor], name = "backend")
  //init Hazelcast
  getInstance()

}
