// Copyright (c) 2016-2022 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package floating.demo

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.vdom.TagOf
import org.scalajs.dom
import floatingui._
import floatingui.hooks._

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.html
import org.scalajs.dom
// import floatingui.raw.floatingUiReactDom.anon.Element
import floatingui.raw.floatingUiReactDom.{ typesMod => reactTypesMod }
import floatingui.raw.floatingUiReactDom.mod
import react.common._

import js.annotation._
import japgolly.scalajs.react.feature.ReactFragment

final case class Tooltip(trigger: VdomTag) extends ReactFnProps[Tooltip](Tooltip.component)

object Tooltip {
  type Props = Tooltip

  val component =
    ScalaFnComponent
      .withHooks[Props]
      .useRefToAnyVdom
      .useRefToAnyVdom
      .useFloatingBy { (_, ar, _) =>
        reactTypesMod
          .UseFloatingProps[reactTypesMod.ReferenceType]()
          // .setStrategy(Strategy.Fixed)
          .setPlacement(Placement.Right)
          .setWhileElementsMounted(mod.autoUpdate)
          .setMiddleware(
            js.Array(
              // offset(10)
              flip
              // shift(ShiftOptions().setPadding(5)),
              // arrow(
              //   Element(ar.raw.asInstanceOf[js.Any])
              // )
            )
          )
      }
      .useState(false)
      .render { (props, a, b, h, open) =>
        println("----")
        org.scalajs.dom.window.console.log(h)
        org.scalajs.dom.window.console.log(h.reference())
        org.scalajs.dom.window.console.log(h.refs.reference)
        org.scalajs.dom.window.console.log(h.refs.floating)
        println("----")
        // val refRef   =
        //   Ref.fromJs(h.reference().asInstanceOf[facade.React.RefHandle[dom.Node | Null]])
        val floatRef =
          Ref.fromJs(h.refs.floating.asInstanceOf[facade.React.RefHandle[html.Div | Null]])
        // re
        // val arrowOpt  = h.middlewareData.arrow.toOption
        // val placement = h.placement
        //
        // val arrowStyle: Map[String, String | Int] =
        //   (arrowOpt.flatMap(_.x.toOption), arrowOpt.flatMap(_.y.toOption)) match {
        //     case (Some(x), Some(y)) =>
        //       Map("left" -> s"${x}px", "top" -> s"${y}px", "right" -> "", "bottom" -> "")
        //     case (Some(x), None)    =>
        //       Map("left" -> s"${x}px", "top" -> s"", "right" -> "", "bottom" -> "")
        //     case (None, Some(y))    =>
        //       Map("left" -> "", "top" -> s"${y}px", "right" -> "", "bottom" -> "")
        //     case _                  => Map.empty
        //   }
        //
        // val arrowShift = "-4px"
        //
        // val placementStyle: Map[String, String | Int] = placement match {
        //   // case Placement.Top | Placement.TopStart | Placement.TopEnd          => Map("bottom" -> arrowShift)
        //   case Placement.Top    => Map("bottom" -> arrowShift)
        //   // case Placement.Bottom | Placement.BottomStart | Placement.BottomEnd =>
        //   case Placement.Bottom =>
        //     Map("top" -> arrowShift)
        //   // case Placement.Left | Placement.LeftStart | Placement.LeftEnd       =>
        //   case Placement.Left   =>
        //     Map("right" -> arrowShift)
        //   // case Placement.Right | Placement.RightStart | Placement.RightEnd    =>
        //   case Placement.Right  =>
        //     Map("left" -> arrowShift)
        //   case _                => Map.empty
        // }

        // val r = props.trigger(^.onClick --> open.setState(true)).withRef(refRef)
        // val r = <.button(^.untypedRef := refRef)("Test") //.withRef(refRef)
        println(s"a ${a.raw.current}")
        val r = <.button(^.untypedRef := a)("Test")
        val f = //if (open.value) {
          <.div(
            ^.cls   := "tooltip",
            // ^.untypedRef := b,
            ^.style := Style(
              Map("position" -> h.strategy.toString, "left" -> s"${h.x}px", "top" -> s"${h.y}px")
            ).toJsObject,
            "My tooltip autoplaced 1 which is now quite a bit longer"
            // <.div(^.id := "arrow", ^.style := Style(arrowStyle ++ placementStyle).toJsObject)
            //   .withRef(a)
          ).withRef(b)
        //} else EmptyVdom

        h.reference(a.raw.current.asInstanceOf[dom.Element])
        h.floating(b.raw.current.asInstanceOf[dom.HTMLElement])
        ReactFragment(r, f)
      }
}
