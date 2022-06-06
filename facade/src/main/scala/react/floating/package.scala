// Copyright (c) 2016-2022 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

import floatingui.raw.floatingUiCore.typesMod
import floatingui.raw.floatingUiCore.typesMod.Placement
import floatingui.raw.floatingUiCore.typesMod.Strategy
import floatingui.raw.floatingUiCore.typesMod.Side
// import floatingui.raw.floatingUiCore.typesMod.ComputePositionReturn
// import floatingui.raw.floatingUiDom.anon.PartialShiftOptionsDetect
import floatingui.raw.floatingUiDom.anon.{ Element => DomElement }
import floatingui.raw.floatingUiDom.anon.PartialAutoPlacementOptio
import floatingui.raw.floatingUiReactDomInteractions.anon.PartialUseFloatingPropsRe
// import floatingui.raw.floatingUiReactDom.anon.OmitPartialComputePositio
// import floatingui.raw.floatingUiReactDom.anon.Element
// import floatingui.raw.floatingUiCore.mod
import floatingui.raw.floatingUiReactDomInteractions.{ mod => reactMod }
import floatingui.raw.floatingUiReactDomInteractions.{ typesMod => reactTypesMod }
// import floatingui.raw.floatingUiReactDomInteractions.{ typesMod => reactInteractionsTypesMod }
import floatingui.raw.floatingUiDom.typesMod.Middleware
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
  type UseFloatingProps = PartialUseFloatingPropsRe
  val UseFloatingProps = PartialUseFloatingPropsRe
  // val UseFloatingProps = reactTypesMod.UseFloatingProps[reactTypesMod.ReferenceType]
  type UseFloatingReturn = reactTypesMod.UseFloatingReturn[reactTypesMod.ReferenceType]
  type FloatingContext   = reactTypesMod.FloatingContext[reactTypesMod.ReferenceType]

  type ElementProps = reactTypesMod.ElementProps
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
  type AutoPlacementOptions = PartialAutoPlacementOptio
  val AutoPlacementOptions = PartialAutoPlacementOptio

  lazy val flip: Middleware                                    = reactMod.flip()
  def offset(i: Int): Middleware                               = reactMod.offset(i).asInstanceOf[Middleware]
  // lazy val shift                   = reactMod.shift()
  // def shift(options: ShiftOptions) = reactMod.shift(options)
  def arrow(i: DomElement): Middleware                         = reactMod.arrow(i)
  lazy val autoPlacement: Middleware                           = reactMod.autoPlacement()
  def autoPlacement(options: AutoPlacementOptions): Middleware = reactMod.autoPlacement(options)
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
