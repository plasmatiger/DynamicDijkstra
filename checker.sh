find . -maxdepth 5 -name "*.class" -type f -delete

vpl_junit_timeout=60

vpl_junit_version=$(basename  $(ls vpl-junit*) .b64)

export CLASSPATH=$CLASSPATH:./$vpl_junit_version

javac -J-Xmx16m -Xlint:deprecation *.java

if [ "$?" -ne "0" ] ; then
  echo "Comment :=>> Not compiled"
  exit 0
fi

timeout $vpl_junit_timeout java -jar $vpl_junit_version EvalTest