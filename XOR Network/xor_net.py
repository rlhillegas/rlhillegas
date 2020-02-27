# xor_net.py
# This program uses a 3 layer MLP to learn XOR logic.  The network consists of 2 input
# nodes, 3 hidden nodes, and 1 output node.  PyTorch was used to create the network.
#
# Riley Hillegas
# 02/26/2020

import torch
import torch.nn as nn
import torch.optim as optim

EPOCHS = 15000


class MLP(nn.Module):
    def __init__(self, input_size, hidden_size, output_size):
        super().__init__()
        self.fc1 = nn.Linear(input_size, hidden_size)
        self.fc2 = nn.Linear(hidden_size, output_size)

    def forward(self, x):
        hidden = self.fc1(x)
        hidden = torch.sigmoid(hidden)
        output = self.fc2(hidden)
        output = torch.sigmoid(output)
        return output


def main():
    X = torch.Tensor([
        [0, 0],
        [0, 1],
        [1, 0],
        [1, 1]]
    )

    Y = torch.Tensor([
        [0],
        [1],
        [1],
        [0]]
    )

    # Instantiate the model instance, define the loss function, and define the optimization algorithm.
    model = MLP(2, 3, 1)
    loss_func = nn.BCELoss()
    optimizer = optim.Adam(model.parameters())

    # Training loop.
    for i in range(EPOCHS):
        optimizer.zero_grad()
        Y_ = model(X)
        loss = loss_func(Y_.squeeze(), Y)
        loss.backward()
        optimizer.step()

        if i % 1000 == 0:
            print(f"Epoch: {i}, Loss: {loss.data.numpy()}")

    # Make a prediction with each of the XOR input scenarios.
    print(f'model([0, 0]): {model(torch.Tensor([0, 0]))}')
    print(f'model([0, 1]): {model(torch.Tensor([0, 1]))}')
    print(f'model([1, 0]): {model(torch.Tensor([1, 0]))}')
    print(f'model([1, 1]): {model(torch.Tensor([1, 1]))}')


if __name__ == '__main__':
    main()
