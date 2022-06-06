// Copyright (c) 2016-2022 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

import floatingui.raw.floatingUiCore.typesMod
import floatingui.raw.floatingUiCore.typesMod.Placement
import floatingui.raw.floatingUiCore.typesMod.Strategy
import floatingui.raw.floatingUiCore.typesMod.Side
// import floatingui.raw.floatingUiCore.typesMod.ComputePositionReturn
// import floatingui.raw.floatingUiDom.anon.PartialShiftOptionsDetect
// import floatingui.raw.floatingUiDom.anon
// import floatingui.raw.floatingUiReactDom.anon.OmitPartialComputePositio
// import floatingui.raw.floatingUiReactDom.anon.Element
// import floatingui.raw.floatingUiCore.mod
import floatingui.raw.floatingUiReactDom.{ mod => reactMod }
import floatingui.raw.floatingUiReactDom.{ typesMod => reactTypesMod }
// import floatingui.raw.floatingUiCore.arrowMod
import japgolly.scalajs.react._
import japgolly.scalajs.react.facade.React
import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.|
import org.scalajs.dom.html
import org.scalajs.dom
import org.w3c.dom.html.HTMLElement

package object floatingui {
  type UseFloatingProps  = reactTypesMod.UseFloatingProps[reactTypesMod.ReferenceType]
  // val UseFloatingProps = reactTypesMod.UseFloatingProps[reactTypesMod.ReferenceType]
  type UseFloatingReturn = reactTypesMod.UseFloatingReturn[reactTypesMod.ReferenceType]
  // @js.native
  // trait UseFloatingReturn extends js.Object {
  //   val reference: React.RefHandle[dom.Node | Null]
  //   val floating: React.RefHandle[dom.Node | Null]
  //   val x: js.UndefOr[Double]
  // }

  // implicit def Compute2Omni(c: floatingui.ComputePosition): ComputePositionReturn = {
  //   val p: ComputePositionReturn = (new js.Object()).asInstanceOf[ComputePositionReturn]
  //   // p.placement = c.placement
  //   p.strategy = c.strategy
  //   // p.middleware = c.middleware.toJSArray
  //   p
  // }

  // type ShiftOptions = PartialShiftOptionsDetect
  // val ShiftOptions = PartialShiftOptionsDetect

  lazy val flip = reactMod.flip()
  // def offset(i: Int)               = reactMod.offset(i)
  // lazy val shift                   = reactMod.shift()
  // def shift(options: ShiftOptions) = reactMod.shift(options)
  // def arrow(i: Element)            = reactMod.arrow(i)
  // def arrow(i: js.Any) = mod.arrow(arrowMod.Options().setElement(i))
  // def arrow(i: HTMLElement) = mod.arrow(i)

  object Placement {
    lazy val Top: Placement    = Side.top
    lazy val Right: Placement  = Side.right
    lazy val Bottom: Placement = Side.bottom
    lazy val Left: Placement   = Side.left
    // lazy val TopStart: Placement = "top-start"
    // lazy val TopEnd              = "top-end"
    // lazy val RightStart          = "right-start"
    // lazy val RightEnd            = "right-end"
    // lazy val BottomStart         = "bottom-start"
    // lazy val BottomEnd           = "bottom-end"
    // lazy val LeftStart           = "left-start"
    // lazy val LeftEnd             = "left-end"
  }

  object Strategy {
    lazy val Absolute: Strategy = typesMod.Strategy.absolute
    lazy val Fixed: Strategy    = typesMod.Strategy.fixed
  }
}

package floatingui {
  // final case class ComputePosition(
  //   placement: js.UndefOr[typesMod.Placement] = js.undefined,
  //   strategy:  js.UndefOr[typesMod.Strategy] = js.undefined
  //   // middleware: List[typesMod.Middleware] = Nil
  // ) {
  //   def toProps: UseFloatingProps = reactTypesMod.UseFloatingProps()
  // }

}
