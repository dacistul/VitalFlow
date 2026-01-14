/* VitalFlow - Insulin Pump Verification Model */

mtype = { LOW, NORMAL, HIGH, CRITICAL_HIGH };
mtype = { IDLE, PUMPING, ALARM, EMPTY };

int glucose_level = 100;
int insulin_reservoir = 10;
bool pump_active = false;
bool alarm_triggered = false;

/* Channels for communication */
chan sensor_data = [0] of { int };
chan pump_cmd = [0] of { bool };

active proctype Sensor() {
    do
    :: true -> 
        if
        :: glucose_level > 40 -> glucose_level = glucose_level - 10;
        :: glucose_level < 300 -> glucose_level = glucose_level + 20;
        :: true -> skip;
        fi;
        sensor_data ! glucose_level;
    od
}

active proctype Controller() {
    int current_level;
    
    do
    :: sensor_data ? current_level ->
        if
        :: (current_level < 70) -> 
            pump_cmd ! false;
        :: (current_level >= 200) -> 
            pump_cmd ! true;
        :: else -> 
            pump_cmd ! false;
        fi
    od
}

active proctype Pump() {
    bool trigger;
    
    do
    :: pump_cmd ? trigger ->
        if
        :: trigger && (insulin_reservoir > 0) ->
            atomic {
                pump_active = true;
                insulin_reservoir = insulin_reservoir - 1;
                glucose_level = glucose_level - 15;
                printf("ACTION: Insulin Delivered. Remaining: %d\n", insulin_reservoir);
            }
        :: trigger && (insulin_reservoir == 0) ->
             alarm_triggered = true;
             printf("ALARM: Reservoir Empty!\n");
        :: !trigger ->
            pump_active = false;
            printf("STATUS: Pump Idle. Glucose: %d\n", glucose_level);
        fi
    od
}

ltl safety_check { [] !(pump_active && (glucose_level < 70)) }
