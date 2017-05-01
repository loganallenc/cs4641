# used tutorial https://medium.com/emergent-future/simple-reinforcement-learning-with-tensorflow-part-0-q-learning-with-tables-and-neural-networks-d195264329d0

import gym
import numpy as np

env = gym.make('FrozenLake-v0')

lr = 0.75
gamma = 0.99
num_episodes = 25

# train new Q function
for episode in range(num_episodes):
    num_iter_to_train = pow(10, 5)

    Q = np.zeros([env.observation_space.n, env.action_space.n])
    for train in xrange(num_iter_to_train):
        state = env.reset()
        for _ in range(10000):
            action = np.argmax( Q[state, :] + np.random.randn(1, env.action_space.n)*(1./(episode+1)) )
            state_new, reward, done, _ = env.step(action)
            Q[state, action] = Q[state, action] + lr*(reward + gamma*np.max(Q[state_new,:]) - Q[state, action])
            state = state_new
            if done:
                break

    sum_rewards = 0
    # test out policy thousand times and take the average performance
    for i in xrange(1000):
        state = env.reset()
        # apply policy
        for t in xrange(1000):
            action = np.argmax( Q[state, :] + np.random.randn(1, env.action_space.n)*(1./(episode+1)) )
            state, reward, done, info = env.step(action)
            if done:
                sum_rewards += reward
                break

    print "{0}, {1}, {2}".format(episode, sum_rewards / 1000.0, num_iter_to_train)
