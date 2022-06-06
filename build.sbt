import org.scalajs.linker.interface.ModuleSplitStyle

val scalaJsReact       = "2.1.1"
val scalaJSReactCommon = "0.17.0"

/* ScalablyTyped configuration */
enablePlugins(ScalablyTypedConverterGenSourcePlugin)

Global / onChangedBuildSource := ReloadOnSourceChanges

inThisBuild(
  Seq(
    homepage                      := Some(url("https://github.com/cquiroz/scalajs-floating-ui")),
    Global / onChangedBuildSource := ReloadOnSourceChanges
  ) ++ lucumaPublishSettings
)

addCommandAlias(
  "restartWDS",
  "; demo / Compile / fastOptJS / stopWebpackDevServer; demo / Compile /fastOptJS / startWebpackDevServer"
)

lazy val facade = project
  .in(file("facade"))
  .settings(name := "facade")
  .settings(
    // crossScalaVersions := Seq("2.13.7", "3.1.0"),
    libraryDependencies ++= Seq(
      "com.github.japgolly.scalajs-react" %%% "core"   % scalaJsReact,
      "io.github.cquiroz.react"           %%% "common" % scalaJSReactCommon
    ),
    // shade into another package
    stOutputPackage         := "floatingui.raw",
    /* javascript / typescript deps */
    Compile / npmDependencies ++= Seq(
      // "@floating-ui/react-dom"              -> "0.7.1",
      "@floating-ui/react-dom-interactions" -> "0.6.3"
    ),
    /* disabled because it somehow triggers many warnings */
    scalaJSLinkerConfig ~= (_.withSourceMap(false)),
    // because npm is slow
    useYarn                 := true,
    stSourceGenMode         := SourceGenMode.ResourceGenerator,
    stUseScalaJsDom         := true,
    scalacOptions ~= (_.filterNot(
      Set(
        // By necessity facades will have unused params
        "-Wdead-code",
        "-Ywarn-dead-code",
        "-Wunused:params",
        "-Wunused:imports",
        "-Wunused:explicits"
      )
    )),
    Compile / doc / sources := Seq(),
    // focus only on these libraries
    // stMinimize              := Selection.AllExcept("@svgdotjs/svg.js")
    stMinimize              := Selection.All,
    stMinimizeKeep ++= List(
      "floatingUiReactDom.mod.^",
      "floatingUiReactDom.mod.computePosition",
      "floatingUiReactDom.mod.flip",
      "floatingUiReactDom.mod.offset",
      "floatingUiReactDom.mod.arrow",
      "floatingUiReactDom.mod.autoPlacement",
      "floatingUiReactDom.mod.useHover",
      "floatingUiReactDom.mod.useFloating"
    )
  )
  // .settings(lucumaScalaJsSettings: _*)
  .enablePlugins(ScalablyTypedConverterGenSourcePlugin)

lazy val demo =
  project
    .in(file("demo"))
    .enablePlugins(ScalaJSPlugin)
    .settings(
      test            := {},
      libraryDependencies ++= Seq(
        "com.github.japgolly.scalajs-react" %%% "core" % scalaJsReact
      ),
      Compile / fastLinkJS / scalaJSLinkerConfig ~= { _.withSourceMap(false) },
      Compile / fullLinkJS / scalaJSLinkerConfig ~= { _.withSourceMap(false) },
      scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.ESModule) },
      Compile / fastLinkJS / scalaJSLinkerConfig ~= (_.withModuleSplitStyle(
        ModuleSplitStyle.SmallestModules
      )),
      Compile / fullLinkJS / scalaJSLinkerConfig ~= (_.withModuleSplitStyle(
        ModuleSplitStyle.FewestModules
      )),
      publish / skip  := true,
      publish         := {},
      publishLocal    := {},
      publishArtifact := false,
      Keys.`package`  := file("")
    )
    .dependsOn(facade)
