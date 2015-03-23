package com.scalakata.metadoc
package plugin

import scala.tools.nsc.{Global, Phase, SubComponent}
import scala.meta.internal.hosts.scalac.{PluginBase => ScalahostPlugin}
import scala.tools.nsc.plugins.{Plugin => NscPlugin, PluginComponent => NscPluginComponent}
import org.scalameta.reflection._

class MetadocPlugin(val global: Global) extends ScalahostPlugin with Metadoc {
  val name = "Metadoc"
  val description = "Scaladoc reloaded"
  val components = List[NscPluginComponent](ConvertComponent, ExampleComponent)

  object ExampleComponent extends NscPluginComponent {
    val global: MetadocPlugin.this.global.type = MetadocPlugin.this.global
    import global._

    override val runsAfter = List("typer")
    override val runsRightAfter = Some("convert")
    val phaseName = "metadoc"
    override def description = "Scaladoc reloaded"

    override def newPhase(prev: Phase): StdPhase = new StdPhase(prev) {
      var alreadyRun = false
      override def apply(unit: CompilationUnit): Unit = {
        if (!alreadyRun) {
          example(global.currentRun.units.toList.map(_.body.metadata("scalameta").asInstanceOf[scala.meta.internal.ast.Source]))
          alreadyRun = true
        }
      }
    }
  }
}
