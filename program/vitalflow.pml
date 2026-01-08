/* VitalFlow - Insulin Pump Verification Model */

mtype = { LOW, NORMAL, HIGH, CRITICAL_HIGH };
mtype = { IDLE, PUMPING, ALARM, EMPTY };

/* Shared Variables */
int glucose_level = 100;
int insulin_reservoir = 10; /* Units of insulin available */
bool pump_active = false;
bool alarm_triggered = false;

/* Channels for communication */
chan sensor_data = [0] of { int };
chan pump_cmd = [0] of { bool };

/* 1. Sensor Process: Simulates changing glucose levels */
active proctype Sensor() {
    do
    :: true -> 
        if
        :: glucose_level > 40 -> glucose_level = glucose_level - 10; /* Sugar drops */
        :: glucose_level < 300 -> glucose_level = glucose_level + 20; /* Sugar rises */
        :: true -> skip; /* Stays same */
        fi;
        sensor_data ! glucose_level; /* Send data to controller */
    od
}

/* 2. Controller Process: Decides logic */
active proctype Controller() {
    int current_level;
    
    do
    :: sensor_data ? current_level ->
        if
        :: (current_level < 70) -> 
            /* Safety Critical: Stop Pump */
            pump_cmd ! false;
        :: (current_level >= 200) -> 
            /* High Sugar: Activate Pump */
            pump_cmd ! true;
        :: else -> 
            /* Normal range: Idle */
            pump_cmd ! false;
        fi
    od
}

/* 3. Pump Process: Executes the mechanical action */
active proctype Pump() {
    bool trigger;
    
    do
    :: pump_cmd ? trigger ->
        if
        :: trigger && (insulin_reservoir > 0) ->
            atomic {
                pump_active = true;
                insulin_reservoir = insulin_reservoir - 1;
                glucose_level = glucose_level - 15; /* Insulin lowers sugar */
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

/* 4. Linear Temporal Logic (LTL) Properties to Verify in Spin */

/* Safety: It is never the case that pump is active AND glucose is low */
/* Run with: spin -run -a -N safety_check vitalflow.pml */
ltl safety_check { [] !(pump_active && (glucose_level < 70)) }
