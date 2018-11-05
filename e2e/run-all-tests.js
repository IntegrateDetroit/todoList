const newman = require('newman');
const fs = require( 'fs' )

function printUsage(){
  console.log('USAGE: node test-runner.js <collection-name> <environment-name>');
}

const runTestFor = (collection, env) => {
  console.log( `we r running da test! ${collection} ${env}` )

  if(!(collection && env)) {
    printUsage();
    process.exit(1);
  }
  
  const runConfig = {
    collection: './collections/' + collection,
    environment: './env/' + env,
    reporters: 'cli'
  };
  
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

const pairs = text.split(";\n");

let run = async () => {
  pairs.forEach(pair => {
    let [ collection, environment ] = pair.split( ', ' )
    environment = environment.replace( ';', '' );

    runTestFor( collection, environment );
  });
}

(async () => {
  await run();
})()
