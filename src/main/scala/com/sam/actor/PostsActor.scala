package com.sam.actor

import akka.actor.{Actor, RootActorPath}
import akka.cluster.{Cluster, Member, MemberStatus}
import akka.cluster.ClusterEvent.{CurrentClusterState, MemberUp}
import com.sam.comm.{PostsActorRegister, PostsCreateJob, PostsGetJob}
import com.sam.data.HazelcastStore

class PostsActor extends Actor with HazelcastStore{
  val cluster = Cluster(context.system)

  // subscribe to cluster changes, MemberUp
  // re-subscribe when restart
  override def preStart(): Unit = cluster.subscribe(self, classOf[MemberUp])
  override def postStop(): Unit = cluster.unsubscribe(self)


  override def receive: Receive = {
    case createJob:PostsCreateJob =>
      println("get data " + createJob.posts)
      sender() ! savePosts(createJob.posts)

    case getJob:PostsGetJob =>
      sender() ! getPosts(getJob.postid)

    case state: CurrentClusterState =>
      state.members.filter(_.status == MemberStatus.Up) foreach register

    case MemberUp(m) => register(m)
  }


  def register(member: Member): Unit =
    if (member.hasRole("frontend"))
      context.actorSelection(RootActorPath(member.address) / "user" / "frontend") ! PostsActorRegister
}
