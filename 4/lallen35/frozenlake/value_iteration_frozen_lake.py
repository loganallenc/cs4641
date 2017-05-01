# Solving as MDP using Value Iteration Algorithm

import gym
import numpy as np
import datetime

def iterate_value_function(v_inp, gamma, env):
    ret = np.zeros(env.unwrapped.nS)
    for sid in range(env.unwrapped.nS):
        temp_v = np.zeros(env.unwrapped.nA)
        for action in range(env.unwrapped.nA):
            for (prob, dst_state, reward, is_final) in env.unwrapped.P[sid][action]:
                temp_v[action] += prob*(reward + gamma*v_inp[dst_state]*(not is_final))
        ret[sid] = max(temp_v)
    return ret

def build_greedy_policy(v_inp, gamma, env):
    new_policy = np.zeros(env.unwrapped.nS)
    for state_id in range(env.unwrapped.nS):
        profits = np.zeros(env.unwrapped.nA)
        for action in range(env.unwrapped.nA):
            for (prob, dst_state, reward, is_final) in env.unwrapped.P[state_id][action]:
                profits[action] += prob*(reward + gamma*v[dst_state])
        new_policy[state_id] = np.argmax(profits)
    return new_policy


env = gym.make('FrozenLake-v0')
gamma = 0.9
n_rounds = 800

print "timestep, accuracy, iterations"
for t_rounds in xrange(n_rounds):
    # init env and value function
    observation = env.reset()
    v = np.zeros(env.unwrapped.nS)

    num_iter_to_train = pow(10, t_rounds / 100 + 1)

    # solve MDP
    for _ in xrange(num_iter_to_train):
        v_old = v.copy()
        v = iterate_value_function(v, gamma, env)
        if np.all(v == v_old):
            break
    policy = build_greedy_policy(v, gamma, env).astype(np.int)

    sum_rewards = 0
    # test out policy thousand times and take the average performance
    for i in xrange(1000):
        observation = env.reset()
        # apply policy
        for t in xrange(1000):
            action = policy[observation]
            observation, reward, done, info = env.step(action)
            if done:
                sum_rewards += reward
                break

    print "{0}, {1}, {2}".format(t_rounds, sum_rewards / 1000.0, num_iter_to_train)

print datetime.datetime.now().time()
