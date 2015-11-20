package io.github.mandar2812.dynaml.kernels

import breeze.linalg.{DenseMatrix, norm, DenseVector}

/**
 * @author mandar2812
 * Cauchy Kernel given by the expression
 * K(x,y) = 1/(1 + ||x-y||**2/sigma**2)
 */
class CauchyKernel(si: Double = 1.0)
  extends SVMKernel[DenseMatrix[Double]]
  with LocalSVMKernel[DenseVector[Double]]
  with Serializable {
  override val hyper_parameters = List("sigma")

  private var sigma: Double = si

  def setsigma(b: Double): Unit = {
    this.sigma = b
  }

  override def evaluate(x: DenseVector[Double], y: DenseVector[Double]): Double =
    1/(1 + math.pow(norm(x-y, 2)/sigma, 2))

  override def setHyperParameters(h: Map[String, Double]) = {
    assert(hyper_parameters.forall(h contains _),
      "All hyper parameters must be contained in the arguments")
    this.sigma = h("sigma")
    this
  }
}

class CauchyCovFunc(private var sigma: Double)
  extends LocalSVMKernel[Double] {
  override val hyper_parameters: List[String] = List("bandwidth")

  override def evaluate(x: Double, y: Double): Double = {
    1/(1 + math.pow((x-y)/sigma, 2))
  }
}