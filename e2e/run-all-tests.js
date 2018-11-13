const newman = require('newman');
const fs = require('fs');
const os = require('os');


const getConfig = ( collection, environment ) => {
  if (os.platform == 'darwin')
    return {
      collection: `./collections/${collection}`,
      environment: `./env/${environment}`,
      delayRequest: 2000,
      reporters: 'cli'
  } 
  return {
      collection: `.\\collections\\${collection}`,
      environment: `.\\env\\${environment}`,
      delayRequest: 2000,
      reporters: 'cli'
    }
}

const parseConfigFile = ( configFile ) => {
  if(os.platform == "darwin") 
    return configFile.split(";\n")
  return configFile.split(";\r\n")
}

const printUsage = () => {
  console.log('USAGE: node test-runner.js <collection-name> <environment-name>');
}

// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

const runTestFor = async (collection, env) => {
  if(!(collection && env)) {
    printUsage();
    process.exit(1);
  }

  const runConfig = getConfig( collection, env )
  
  return new Promise(
    ( resolve, reject ) => {
      newman.run(
        runConfig, 
        (err, summary) => {
          // console.log(`Running test: ${collection} ${env}`)
          if (err) {
            console.log(`ERROR for ${ collection }`, err);
            reject()
          }
          if (summary.run.failures && summary.run.failures.length > 0) {
            console.log(`FAILURE for ${ collection }`, summary.run.failures);
            reject()
          }
          resolve()
        })
    })
  
}

// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

let text = fs.readFileSync( './testPairs.txt', { encoding: 'UTF-8' } )
let pairs = parseConfigFile( text );

(async () => {

  for( let index in pairs ) {
    let pair = pairs[ index ]

    let [ collection, environment ] = pair.split( ', ' )
    environment = environment.replace( ';', '' );

    try {
      await runTestFor( collection, environment );
    } catch( exception ) {
      process.exit( 1 )
    }
  }

})()