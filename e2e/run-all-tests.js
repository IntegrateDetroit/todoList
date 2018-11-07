const newman = require('newman');
const fs = require('fs');
const os = require('os');

function printUsage(){
  console.log('USAGE: node test-runner.js <collection-name> <environment-name>');
}

const runTestFor = (collection, env) => {
  console.log(`Running test: ${collection} ${env}`)
  console.log(collection)
  console.log(env)
  if(!(collection && env)) {
    printUsage();
    process.exit(1);
  }
  let runConfig;
  console.log("PLATFORM " + os.platform);
  if (os.platform == 'darwin'){
    runConfig = {
      collection: './collections/' + collection,
      environment: './env/' + env,
      delayRequest: 2000,
      reporters: 'cli'
    };
  } else {
    runConfig = {
      collection: '.\\collections\\' + collection,
      environment: '.\\env\\' + env,
      delayRequest: 2000,
      reporters: 'cli'
    }
  }
  
  newman.run(runConfig, (err, summary, run) => {
    if (err) {
      console.log(`ERROR for ${ collection }`, err);
      process.exit(1);
    }
    if (summary.run.failures && summary.run.failures.length > 0) {
      console.log(`FAILURE for ${ collection }`, summary.run.failures);
      process.exit(1);
    }
  });
  
}

let text = fs.readFileSync( './testPairs.txt', { encoding: 'UTF-8' } )

let pairs;
if(os.platform == "darwin"){
  pairs = text.split(";\n");
}else{
  pairs = text.split(";\r\n")
}

pairs.forEach(pair => {
  let [ collection, environment ] = pair.split( ', ' )
  environment = environment.replace( ';', '' );

  runTestFor( collection, environment );
});
