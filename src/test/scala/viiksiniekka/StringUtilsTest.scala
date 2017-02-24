package viiksiniekka

import org.scalatest.FunSuite

class StringUtilsTest extends FunSuite {
  test("Can convert ordinary text to camel case") {
    assert("projectManager" === StringUtils.ordinaryTextToCamelCase("Project manager"))
  }

  test("Can convert ordinary text to constant case") {
    assert("PROJECT_MANAGER" === StringUtils.ordinaryTextToConstantCase("Project Manager"))
  }

  test("Can convert camel case to constant case") {
    assert("PROJECT_MANAGER" === StringUtils.camelCaseToConstantCase("ProjectManager"))
  }
}
