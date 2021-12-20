package floating

import japgolly.scalajs.react._
import floatingui.raw.floatingUiReactDom.mod
import floatingui.raw.floatingUiReactDom.anon.OmitPartialComputePositio
import floatingui.raw.floatingUiReactDom.srcMod.UseFloatingReturn

object HooksApiExt {
  // val hook1  =
  //   CustomHook.unchecked[OmitPartialComputePositio, UseFloatingReturn] { pos =>
  //     println(pos.placement); mod.useFloating()
  //   }
  // val jsHook = CustomHook.unchecked[OmitPartialComputePositio, UseFloatingReturn] { pos =>
  //   println(pos.placement); val res = mod.useFloating(pos)
  //   // res.update()
  //   res
  // }
  val hook =
    // CustomHook.unsafe
    CustomHook[ComputePosition]
      // .withHooks[Unit]
      // .useCallback(())
      // .useCallback((pos: OmitPartialComputePositio) => mod.useFloating(pos))
      // .useCallbackWithDeps[OmitPartialComputePositio, UseFloatingReturn](pos)(pos =>
      //   mod.useFloating(pos)
      // )
      // println(pos.placement); mod.useFloating(pos)
      .buildReturning { pos =>
        mod.useFloating(pos)
      }
  // .buildReturning((p, v) => v.value)

  sealed class Primary[Ctx, Step <: HooksApi.AbstractStep](api: HooksApi.Primary[Ctx, Step]) {

    final def useFloating(pos: ComputePosition)(implicit
      step:                    Step
    ): step.Next[UseFloatingReturn] =
      useFloatingBy(_ => pos)

    final def useFloatingBy(pos: Ctx => ComputePosition)(implicit
      step:                      Step
    ): step.Next[UseFloatingReturn] =
      // api.customBy(ctx => mod.useFloating(pos(ctx)))
      api.customBy(ctx => hook(pos(ctx)))
  }

  final class Secondary[Ctx, CtxFn[_], Step <: HooksApi.SubsequentStep[Ctx, CtxFn]](
    api: HooksApi.Secondary[Ctx, CtxFn, Step]
  ) extends Primary[Ctx, Step](api) {

    def useFloatingBy(pos: CtxFn[ComputePosition])(implicit
      step:                Step
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
