package me.snov.sns.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, RequestEntity}
import akka.stream.ActorMaterializer
import spray.json._
import me.snov.sns.model.Message

import scala.concurrent.ExecutionContext

object ProducerActor {
  def props(endpoint: String, subscriptionArn: String, topicArn: String) = Props(classOf[ProducerActor], endpoint, subscriptionArn, topicArn)
}

class ProducerActor(endpoint: String, subscriptionArn: String, topicArn: String) extends Actor with ActorLogging {
  def endpointUri = endpoint

  def transformOutgoingMessage(msg: Message) = msg match {
    case snsMsg: Message => {
      implicit val executor: ExecutionContext = this.context.dispatcher

      (Marshal(snsMsg.toJson.toString).to[RequestEntity]).flatMap { entity =>
        implicit val materializer: ActorMaterializer = ActorMaterializer()
        implicit val system = this.context.system
        val request = HttpRequest(uri=endpoint, method=HttpMethods.POST, entity=entity)
        Http().singleRequest(request)
      }

    }
  }

  override def receive = {
    case message: Message => transformOutgoingMessage(message)
  }
}
