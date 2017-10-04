package me.snov.sns.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.http.scaladsl.model.HttpResponse
import spray.json._
import me.snov.sns.model.Message

object ProducerActor {
  def props(endpoint: String, subscriptionArn: String, topicArn: String) = Props(classOf[ProducerActor], endpoint, subscriptionArn, topicArn)
}

class ProducerActor(endpoint: String, subscriptionArn: String, topicArn: String) extends Actor with ActorLogging {
  def endpointUri = endpoint

  def transformOutgoingMessage(msg: Any) = msg match {
    case snsMsg: Message => snsMsg.toJson.toString

//    case snsMsg: Message => new CamelMessage(snsMsg.toJson.toString, Map(
//        CamelMessage.MessageExchangeId -> snsMsg.uuid,
//        "x-amz-sns-message-type" -> "Notification",
//        "x-amz-sns-message-id" -> snsMsg.uuid,
//        "x-amz-sns-subscription-arn" -> subscriptionArn,
//        "x-amz-sns-topic-arn" -> topicArn
//      ))
  }

  override def receive = {
    case message: Message => sender ! transformOutgoingMessage(message)
  }
}
