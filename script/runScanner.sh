[ -f pokeGoTracker.pid ] && echo "pokeGoTracker.pid already exists" && exit 1

java -cp ../lib/json-20160212.jar:../lib/mail-1.4.7.jar:../bin/ ch/lezepito/pokeGoTracker/PokeGoTrackerScheduler & echo $! > pokeGoTracker.pid
