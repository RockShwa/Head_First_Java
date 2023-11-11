# Notes for Using the Terminal and using Git:

- Make sure to save changes with ctrl s

## Terminal Commands:
- run compiled binaries: navigate to bin folder, java <class with main()>

- ls (list) <- lists directory (folder) contents

- ls -l <- lists more details about directory contents

- cd (change directory) [space] [directory you want to go to]
    - .. -> level up from current directory, . -> current directory
    - Can go to any folder, as long as that folder is a subset of current folder
    - Can't go into files (use nano for that :D)

- Home command -> cd ~ (~ represents home)

- mkdir -> make directory, name directory whatever

- rm -> remove files 

- rm -r [directory name] -> remove directory

- touch -> make files, nano -> cd but for files (does other things to)

- git clone [gitHub repo URL] <- SSH

- check if a specific application exists and the version -> [name of app] --version

- open this directory on VS Code -> code .

- git status -> shows recent changes

- Add -> git add [file] or git add * (to add all files)
    - adds files to staging area to be commited

- Commit -> git commit -m "[About Commit]"
    - Save changes to local repository

- Push -> git push 
    - sync GitHub with local git, pushes everything in staging area

## GIT COMMANDS

- git branch [name] -> creates new branch

- git checkout [name] -> selects branch or a commit

- git checkout -b [name] -> creates new branch and checks it out

- git merge [branch] -> pulls specified branch into current branch

- git rebase [branch] -> takes a set of commits on selected branch, copies them, and then puts them under specified branch

- HEAD -> currently checked out commit, always points to the most recent commit. 
    - Detached HEAD -> attaching HEAD to a commit instead of a branch (git checkout [commit hash])
        - use git log to find hashes and type first few characters into commit hash
        - Move upwards one commit at a time -> HEAD/[branchName]^
        - Move upwards a number of times -> HEAD/[branchName]~[num]

- Branch forcing - reassign a branch to a commit with -f
    - git branch -f [branch you want to move] [HEAD^ or ~num <- branch you're moving it to]

- Reversing Changes
    - git reset -> moves a branch reference backwards in time to an older commit, as if the erased commit never existed at all (git reset HEAD^); good for local stuff
    - git reverse -> introduces changes that reverse what's on the commit, but makes a copying commit so sharing changes can happen; good for bigger stuff

- Moving Work Around
    - git cherry-pick [Commit1] [Commit2] [...]
        - copies a series of commits below current location (HEAD)
    - git rebase -i [starting location/target] (Interactive Rebase)
        - opens a UI to show which commits are about to be copied below target of rebase. Also shows commit hashes and messages
        - Can re-order and de-select commits to copy
    - use rebase for branches, and cherry-pick for commits