// Copyright (c) 2016-2022 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package floatingui

import japgolly.scalajs.react._
import floatingui.raw.floatingUiReactDom.mod
// import floatingui.raw.floatingUiDom.anon.PartialShiftOptionsDetect
// import floatingui.raw.floatingUiReactDom.anon.OmitPartialComputePositio
// import floatingui.raw.floatingUiReactDomInteractions.typesMod.UseFloatingReturn
import floatingui.raw.floatingUiReactDom.{ typesMod => reactTypesMod }

object HooksApiExt {
  val floatingHook =
    CustomHook[UseFloatingProps]
      .buildReturning { pos =>
        mod.useFloating[reactTypesMod.ReferenceType](pos)
      }

  sealed class Primary[Ctx, Step <: HooksApi.AbstractStep](api: HooksApi.Primary[Ctx, Step]) {

    final def useFloating(pos: UseFloatingProps)(implicit
      step:                    Step
    ): step.Next[UseFloatingReturn] =
      useFloatingBy(_ => pos)

    final def useFloatingBy(pos: Ctx => UseFloatingProps)(implicit
      step:                      Step
    ): step.Next[UseFloatingReturn] =
      api.customBy(ctx => floatingHook(pos(ctx)))
  }

  final class Secondary[Ctx, CtxFn[_], Step <: HooksApi.SubsequentStep[Ctx, CtxFn]](
    api: HooksApi.Secondary[Ctx, CtxFn, Step]
  ) extends Primary[Ctx, Step](api) {

    def useFloatingBy[RT](pos: CtxFn[UseFloatingProps])(implicit
      step:                    Step
    ): step.Next[UseFloatingReturn] =
      useFloatingBy(step.squash(pos)(_))
  }
}

trait HooksApiExt {
  import HooksApiExt._

  implicit def hooksExtFloating1[Ctx, Step <: HooksApi.AbstractStep](
    api: HooksApi.Primary[Ctx, Step]
  ): Primary[Ctx, Step] =
    new Primary(api)

  implicit def hooksExtFloating2[Ctx, CtxFn[_], Step <: HooksApi.SubsequentStep[Ctx, CtxFn]](
    api: HooksApi.Secondary[Ctx, CtxFn, Step]
  ): Secondary[Ctx, CtxFn, Step] =
    new Secondary(api)
}

object hooks extends HooksApiExt
