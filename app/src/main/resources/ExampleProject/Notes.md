# Counters

#### 0876 Fed Dwarf Herbs
#### 0777 Man's shop open

# Shop

#### 005 Man's Herb Shop

# Items

```
000: Herb
022: Singeing Dagger
```

# NPCs

```
0000: Urist
0001: Man
0054: Bandit
```

# 000 Urist

Location:
X: 1
Z: 99

Piece Number: 5

## Events

### 0010 First Event

Start Condition: `0876 != 0`
Trigger When: Examined
Valid Angle: 120

#### Page 00
Condition: `Have Item Qty. > 1`
Item: `000`

```
if yes:
    say thanks
    take 1 herb
    set 0876 1
else
    angry formatted response
```

#### Page 01
Condition: `Have Item Qty. = 1`
Item: `000`

```
cheer for finding single herb
```

#### Page 02
Condition: `Have Item Qty. = 0`
Item: `000`

```
say hello
```

### 0014 Talking

Start Condition: `0876 = 1`
Trigger When: Examined
Valid Angle: 120

#### Page 00
Condition: None

```
say thanks
```

# 001 Man

Location:
X: 15
Z: 84

## Events

### 0011 Man Response To Feeding Dwarf

Start Condition: `0876 = 1`
Trigger When: Examined
Valid Angle: 360

#### Page 00
Condition: `0777 != 1`

```
if yes:
    display thanks
    set 0777 1
    decrease herb 1
    increase gold 5
else
    display no worries
```
#### Page 01
Condition: `0777 = 1`

```
display greeting
shop 005
```


### 0012 Greeting before feeding dwarf

Start Condition: `0876 != 1`
Trigger When: Examined
Valid Angle: 90

#### Page 00
Condition: None

```
display greetings
```


# 002 Bandit

Location:
X: 26
Z: 65

Appear: Triggered By Event
Drop Item: 022

Times Will Appear: 2

## Events

### 0013 NEW EVENT

Start Condition: None
Trigger When: Approach Rectangle

Rectangle Size:
* East-West: 1.0
* North-South: 2.0

#### Page 00
Condition: None

```
display greeting
```

### 0015 Rect Test 1

Start Condition: None
Trigger When: Approach Rectangle

Rectangle Size:
* East-West: 1.4
* North-South: 1.4

#### Page 00
Condition: None

```
display greeting
```

### 0016 Circ Event 1

* Start Condition: None
* Trigger When: Approach Circle

Circle Size:
* Radius: 2.5

#### Page 00
Condition: None

```
display greeting
```
