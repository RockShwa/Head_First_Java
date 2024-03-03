# Race Conditions and Immutable Data - Dealing with Concurrency Issues

- Concurrency issues: issuses that happen when multiple threads run at the same time
- For example, if two or more threads are trying to access and change a single object's data, BAD things can happen
    - They are both trying to read and write to an object without knowing the other is there, trying to do the SAME thing

## Synchronized